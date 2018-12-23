package model.db;

public class DbException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6942719378230749092L;

	public DbException() {
		super();
	}
	
	public DbException(String message) {
		super(message);
	}

}
