package lab2.action;

import java.util.Vector;

import lab2.model.*;
import lab2.sql.*;

public class InsertAuthorAction {
	private String name;
	private int age;
	private String country;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String execute() {
		MySQL sql = new MySQL();
		Author author = new Author();
		author.set(name, age, country);
		sql.insertAuthor(author);
		author.setID(sql.selectAuthors(name).get(0).getID());
		Vector<Book> books = sql.selectBooks("systemUse");
		for (Book book : books) {
			sql.deleteBook(book);
			book.setAuthorID(author.getID());
			sql.insertBook(book);
		}
		sql.close();
		return "success";
	}
}
