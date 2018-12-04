package model.db;

import java.util.HashMap;

import model.domain.Categorie;

public class TextDbResultReader extends TextDb<String> {
	private static HashMap<String, TextDbResultReader> instances = new HashMap();
	private TextDbResultReader(String bn) {
		super(bn);
	}
	
	public static TextDbResultReader getInstance(String bn) {
		if (!instances.containsKey(bn)) {
			instances.put(bn, new TextDbResultReader(bn));
		}
		return instances.get(bn);
	}

	@Override
	protected String parseInput(String[] str) {
		String result = "een result";
		
		
		
		return result;
	}
}
