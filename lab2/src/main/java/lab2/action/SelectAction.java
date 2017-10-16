package lab2.action;

import java.text.DecimalFormat;

import lab2.model.*;
import lab2.sql.*;

public class SelectAction {
	private String ISBN;
	private Book book;
	private Author author;
	
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public String formatDouble(double s){
	    DecimalFormat fmt = new DecimalFormat("\u00A5##0.00");
	    return fmt.format(s);
	}
	
	public String execute() {
		MySQL sql = new MySQL();
		book = sql.selectBook(ISBN);
		author = sql.selectAuthor(book.getAuthorID());
		sql.close();
		return "success";
	}
}
