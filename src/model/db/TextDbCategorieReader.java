package model.db;

import java.util.HashMap;

import model.domain.Categorie;

public class TextDbCategorieReader extends TextDb<Categorie> {
	private static HashMap<String, TextDbCategorieReader> instances = new HashMap();
	private TextDbCategorieReader(String bn) {
		super(bn);
	}
	
	public static TextDbCategorieReader getInstance(String bn) {
		if (!instances.containsKey(bn)) {
			instances.put(bn, new TextDbCategorieReader(bn));
		}
		return instances.get(bn);
	}

	@Override
	protected Categorie parseInput(String[] str) {
		Categorie cat = null;
		if (str.length == 2) {
			cat = new Categorie(str[0], str[1]);
		} else if( str.length == 3) {
			cat = new Categorie(str[0], str[1], findCategorie(str[2]));
		}
		
		return cat;
	}
	
	public Categorie findCategorie(String naam) {
		for(Categorie cat: this.getItems()) {
			if (cat.getName().equals(naam)) {
				return cat;
			}
		}
		throw new DbException("Categorie with name " + naam + " was not found!");
	}
	
	

}
