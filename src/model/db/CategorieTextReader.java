package model.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Categorie;

public class CategorieTextReader {
	/*bedenkingen:
	 * Categorien in een set steken, om duplicaten te voorkomen?
	 * Een Textreader met een state maken om te kiezen voor Categoriën of vragen?
	 * 
	 */
	
	private File file;
	private TextScanner scanner;
	private List<Categorie> categories;
	
	public CategorieTextReader() {
		//this.file = new File("Categories");
		scanner = TextScanner.getInstance();
		categories = new ArrayList<Categorie>();
	}
	
	public List<Categorie> getCategories() {
		if (categories.size() == 0) {
			readCategories();
		}
		return categories;
	}
	
	private List<Categorie> readCategories() {
		List<String[]> rawList = scanner.getText("Categories.txt");
		for (String[] str : rawList) {
			Categorie cat = null;
			if (str.length == 2) {
				cat = new Categorie(str[0], str[1]);
			} else if( str.length == 3) {
				cat = new Categorie(str[0], str[1], findCategorie(str[2]));
			}
			if (cat != null) categories.add(cat);
		}
		return null;
	}
	
	public Categorie findCategorie(String naam) {
		for(Categorie cat: categories) {
			if (cat.getName().equals(naam)) {
				return cat;
			}
		}
		return null; //mss error throwen ipv null terug te geven??
	}
	
	public void addCategorie(Categorie categorie) {
		this.categories.add(categorie);
		
	}
	
	public void addCategories(List<Categorie> categories) {
		//Same as addCategorie but for multiple at once
		this.categories.addAll(categories);
	}
	
	public void writeCategories(List<Categorie> cate) {
		String towrite = "";
		for (Categorie c: cate) {
			towrite += c.getName() + "-" + c.getDescription();
			if (c.getParent() != null) {
				towrite += "-" + c.getParent().getName();
			}
			towrite+="\n";
		}
		System.out.println(towrite);
		try {
			FileWriter fileWriter = new FileWriter("Categories.txt");
		    fileWriter.write(towrite);
		    fileWriter.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		 
	}
}
