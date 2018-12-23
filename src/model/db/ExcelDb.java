package model.db;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import excel.ExcelPlugin;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.domain.Categorie;
import model.domain.questions.Question;
import model.domain.questions.QuestionFactory;
import model.domain.questions.QuestionTypes;

public class ExcelDb<E> implements DbStrategy<E>{

	@SuppressWarnings("rawtypes")
	private static HashMap<String, ExcelDb> instances = new HashMap<String, ExcelDb>();
	private File file;
	private List<E> items;
	
	private ExcelDb(String file) {
		this.file = new File(file);
		load();
	}
	
	@SuppressWarnings("rawtypes")
	public static ExcelDb getInstance(String file) {
		if (!instances.containsKey(file)) {
			instances.put(file, new ExcelDb(file));
		}
		return instances.get(file);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public void load() {
		List<E> items = new ArrayList<E>();
		ArrayList<ArrayList<String>> data;
		
		
		//reads categories out of excel file
		try {
			data = ExcelPlugin.read(file, 0);
			
			for (ArrayList<String> row : data) {
				Categorie c = makeCategorie(row, items);
				//the reason i pass the 'items' when making a categorie is because some categories require a parent categorie
				//And it has to be searched for in the items
				items.add((E)c);
				//System.out.println(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//reads questions out of excel file
		try {
			data = ExcelPlugin.read(file, 1);
			for (ArrayList<String> row : data) {
				Question q = makeQuestion(row, items);
				//explanation of why passing 'items' to method above
				items.add((E)q);
				//System.out.println(q);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.items = items;
	}	

	@Override
	public void save() {
		try {
			ExcelPlugin excel = new ExcelPlugin(file);
			ArrayList <ArrayList<String>> categories = new ArrayList<ArrayList<String>>();
			ArrayList <ArrayList<String>> questions = new ArrayList<ArrayList<String>>();
			
			for (E item : getItems()) {
				if (item instanceof Categorie) {
					Categorie c = (Categorie)item;
					String[] cSplit = c.toString().split("-");
					categories.add(new ArrayList<String>(Arrays.asList(cSplit)));
				}
				
				if (item instanceof Question) {
					Question q = (Question)item;
					String[] qSplit = q.toString().split("-");
					/*for (int i = 0; i < qSplit.length; i++) {
						System.out.println(qSplit[i]);
					}*/
					questions.add(new ArrayList<String>(Arrays.asList(qSplit)));
				}
			}
			
			try {
				excel.addSheet(categories, 0, "categories");
				excel.addSheet(questions, 1, "questions");
				
				excel.write();
			} catch (BiffException | WriteException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("error while trying to save quiz to excel: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<E> getItems() {
		return items;
	}

	@Override
	public void addItem(E item) {
		items.add(item);
	}

	@Override
	public void removeItem(E item) {
		items.remove(item);
	}

	
	private Question makeQuestion(ArrayList<String> input, List<E> items) {
		String questionClassName = QuestionTypes.valueOf(input.get(0)).getClassName();
		String question = input.get(1);
		ArrayList<String> statements = new ArrayList<String>(Arrays.asList(input.get(2).substring(1, input.get(2).length()-1).split(", ")));
		String feedback = input.size()==5?input.get(4):"";
		String categoryName = input.get(3);
		Categorie cat = null;
		
		for (E item : items) {
			if (item instanceof Categorie) {
				if (((Categorie) item).getName().equals(categoryName)) {
					cat = (Categorie)item;
				}
			}
		}
		
		if (cat == null) throw new DbException("Categorie '" + categoryName + "' was not found!");
		
		Object[] params = statements.get(0).equals("TRUE/FALSE")?new Object[3]:new Object[4];
		
		params[0] = question;
		params[2] = feedback;
		params[1] = cat;
		if (!statements.get(0).equals("TRUE/FALSE")) params[3] = statements;
		
		return QuestionFactory.createQuestion(questionClassName, params);
	}
	
	private Categorie makeCategorie(ArrayList<String> input, List<E> items) {
		if (input.size()==2) return new Categorie(input.get(0), input.get(1));
		else {
			for (E item : items) {
				if (item instanceof Categorie) {
					if (((Categorie) item).getName().equals(input.get(2))) {
						return new Categorie(input.get(0), input.get(1), (Categorie)item);
					}
				}
			}
			throw new DbException("Categorie '" + input.get(2) + "' not found!");
		}
	}
	
	/*public static void main(String[] args) {
	 * 
	 * 
	 * this main method was only used for testing while making this class
	 * keeping it here incase there needs to be any bugs fixed or new feature added.
	 * 
	 * 
		ExcelDb db = new ExcelDb("C:\\Users\\thole_000\\ucll\\OOO\\groep git\\ooo_Th-le_VanCauwenbergh_naujokas\\test.xls");
		
		db.load();
		
		Categorie c = new Categorie("A category", "an example category");
		Question q = new MultipleChoiceQuestion("Is this a good question?", new ArrayList<String>(Arrays.asList("yes", "no")), c, "feedbakc1");
	
		//db.addItem(c);
		//db.addItem(q);
		
		db.save();
	}
	*/
}
