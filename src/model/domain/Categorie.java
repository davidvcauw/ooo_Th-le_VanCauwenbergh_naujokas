package model.domain;

public class Categorie {
	private String name;
	private String description;
	private Categorie parent;
	
	
	public Categorie(String name, String description) {
		setName(name);
		setDescription(description);
	}
	
	public Categorie(String name, String description, Categorie parent) {
		this(name, description);
		setParent(parent);
	}
	
	public void setName(String n) {
		if (n == null || n.trim().isEmpty()) throw new IllegalArgumentException("Name can't be empty!");
		if (n.contains("-")) throw new IllegalArgumentException("Category name can't include '-'!");
		this.name = n;
	}
	
	public void setDescription(String d) {
		if (d == null || d.trim().isEmpty()) throw new IllegalArgumentException("Description can't be empty!");
		if (d.contains("-")) throw new IllegalArgumentException("Category description can't include '-'!");
		this.description = d;
	}
	
	public void setParent(Categorie p) {
		this.parent = p;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Categorie getParent() {
		return this.parent;
	}
	
	@Override
	public String toString() {
		return getName()+"-"+getDescription() + (getParent() == null?"":"-"+this.getParent().getName());
	}
}
