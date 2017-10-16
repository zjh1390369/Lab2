package lab2.action;

import lab2.sql.*;

public class DeleteAction {
	private String ISBN;
	
	public String execute() {
		MySQL sql = new MySQL();
		sql.deleteBook(ISBN);
		return "success";
	}
	
	public String getISBN() {
		return this.ISBN;
	}
	
	public void setISBN(String isbn) {
		this.ISBN = isbn;
	}
}
