package model.db;

import java.io.File;
import java.util.List;

import model.domain.Categorie;

public class CategorieTextReader {
	private File file;
	
	public CategorieTextReader() {
		this.file = new File("Categories");
	}
	
	public List<Categorie> getCategories() {
		//TODO -> implement file reader
		
		return null;
	}
	
	public void addCategorie(Categorie categorie) {
		//TODO -> convert categorie to text and write to file
	}
	
	public void addCategories(List<Categorie> categories) {
		//Same as addCategorie but for multiple at once
	}
}
