package model.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextScanner {
	private File file;
	private static TextScanner instance;
	private TextScanner () {
		
	}
	
	public static TextScanner getInstance() {
		if (instance == null) {
			instance = new TextScanner();
		}
		
		return instance;
	}
	
	public List<String[]> getText(String name) {
		List<String[]> text = new ArrayList<>();
		file = new File(name);
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
}
