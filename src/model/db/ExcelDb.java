package model.db;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import excel.ExcelPlugin;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class ExcelDb<E> implements DbStrategy<E>{

	private ExcelPlugin el;
	@Override
	public void load() {
		
		try{
			ArrayList <ArrayList<String>> list = ExcelPlugin.read(new File("Questions.xls"), 1);
			for (int i = 0; i < list.size();i++){
				ArrayList<String>rijtje = list.get(i);
				for (int j=0;j<rijtje.size();j++){
					System.out.println(rijtje.get(j));
				}
			}
		}
		catch (Exception e){
			System.out.println("lezen excel "+ e.getMessage());
		}
	}	

	@Override
	public void save() throws WriteException, IOException {
		 el = new ExcelPlugin(new File("Questions.xls"));
		el.write();
		
	}

	@Override
	public List<E> getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addItem(E item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeItem(E item) {
		// TODO Auto-generated method stub
		
	}

}
