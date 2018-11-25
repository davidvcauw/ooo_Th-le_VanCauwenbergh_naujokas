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
		setName(name);
		setDescription(description);
		setParent(parent);
	}
	
	private void setName(String n) {
		if (n == null || n.trim().isEmpty()) throw new IllegalArgumentException("Name can't be empty!");
		this.name = n;
	}
	
	private void setDescription(String d) {
		if (d == null || d.trim().isEmpty()) throw new IllegalArgumentException("Description can't be empty!");
		this.description = d;
	}
	
	private void setParent(Categorie p) {
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
}
