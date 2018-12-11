package model.db;

import java.util.List;

public interface DbStrategy<E> {
	public void load();
	public void save();
	public  List<E> getItems();
	
	public void addItem(E item);
	public void removeItem(E item);
}
