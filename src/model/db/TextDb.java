package model.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


public abstract class TextDb<E> implements DbStrategy<E> {
	private String bestandsnaam;
	private List<E> items;
	public TextDb(String bn) {
		this.bestandsnaam = bn;
		this.items = new ArrayList<E>();
		
		load();
		
		File theDir = new File("testdatabase");
		if (!theDir.exists()) {
			try{
		        theDir.mkdir();
		        Properties properties = new Properties();
		        properties.load(this.getClass().getClassLoader().getResourceAsStream("evaluation.properties"));
		        FileOutputStream fr = new FileOutputStream("testdatabase/evaluation.properties");
		        properties.store(fr, "Properties");
		        fr.close();
		    } 
		    catch(SecurityException | IOException se){
		        se.printStackTrace();
		    }   
		}
		
		if(!new File(bn.split("/")[1]+"/"+bn.split("/")[2]).isFile()) { 
			//File file = new File(System.getProperty("user.dir"), bn.split("/")[1]+bn.split("/")[2]);
			try {
				FileWriter fileWriter = new FileWriter(bn.split("/")[1]+"/"+bn.split("/")[2]);
				String towrite = "";
				for (E c: items) {
					towrite += c.toString();
							
					towrite+="\n";
				}
				fileWriter.write(towrite);
			    fileWriter.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} 
	}
	public final void load() {
		items = new ArrayList<E>();
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
					
			towrite+="\n";
		}
		//System.out.println(towrite);
		try {
			
			OutputStream out =  new FileOutputStream(bestandsnaam.replace("src/", ""));
			OutputStreamWriter ow = new OutputStreamWriter(out);
			BufferedWriter bw = new BufferedWriter(ow);
			bw.flush();
			bw.write(towrite);
			bw.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	protected abstract E parseInput(String[] str);
	
	public List<String[]> read() {
		List<String[]> text = new ArrayList<>();
		//File file = new File(bestandsnaam);
		try {
			if (new File(bestandsnaam.split("/")[1]+"/"+bestandsnaam.split("/")[2]).exists()) {
				Scanner sc = new Scanner(new File(bestandsnaam.split("/")[1]+"/"+bestandsnaam.split("/")[2]));
				
				while(sc.hasNextLine()) {
					String nextline = sc.nextLine();
					String[] split = nextline.split("-");
					text.add(split);
				}
				sc.close();
				return text;
			} else {
				String naam = bestandsnaam.replace("src/", "");
				InputStream in = this.getClass().getClassLoader().getResourceAsStream(naam);
				BufferedReader br = new BufferedReader(
					    				new InputStreamReader(in));
				
				String line;
				
				while((line = br.readLine()) != null) {
					String[] split = line.split("-");
					text.add(split);
				}
				
				br.close();
				return text;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DbException("File not found");
		}
	}
	
	public void removeItem(E item) {
		items.remove(item);
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
