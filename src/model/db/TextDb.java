package model.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.domain.Categorie;

public abstract class TextDb<E> implements DbStrategy<E> {
	private String bestandsnaam;
	private List<E> items;
	public TextDb(String bn) {
		this.bestandsnaam = bn;
		this.items = new ArrayList();
	}
	public final void load() {
		items = new ArrayList();
		List<String[]> text = read();
		
		for (String[] str: text) {
			E item = parseInput(str);
			items.add(item);
		}
	}
	
	public final void save() {
		String towrite = "";
		for (E c: items) {
			towrite += c.toString();
					/*c.getName() + "-" + c.getDescription();
			if (c.getParent() != null) {
				towrite += "-" + c.getParent().getName();
			}*/
			towrite+="\n";
		}
		System.out.println(towrite);
		try {
			FileWriter fileWriter = new FileWriter(bestandsnaam);
		    fileWriter.write(towrite);
		    fileWriter.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	protected abstract E parseInput(String[] str);
	
	public List<String[]> read() {
		List<String[]> text = new ArrayList<>();
		File file = new File(bestandsnaam);
		try {
			Scanner sc = new Scanner(file);
			
			while(sc.hasNextLine()) {
				String nextline = sc.nextLine();
				String[] split = nextline.split("-");
				text.add(split);
			}
			sc.close();
			return text;
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			throw new DbException("File not found");
		}
	}
	
	public  List<E> getItems() {
		return items;
	}
	
	public void addItem(E item) {
		items.add(item);
	}
	
	public void addItems(List<E> items) {
		this.items.addAll(items);
	}
	
	
}
