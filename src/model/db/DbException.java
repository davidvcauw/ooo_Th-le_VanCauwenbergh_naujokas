package model.db;

public class DbException extends RuntimeException {
	public DbException() {
		super();
	}
	
	public DbException(String message) {
		super(message);
	}

}
