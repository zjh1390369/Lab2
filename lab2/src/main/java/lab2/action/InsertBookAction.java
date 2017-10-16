package lab2.action;

import lab2.sql.*;
import lab2.model.*;
import java.util.*;
import java.sql.Date;

public class InsertBookAction {
	private String ISBN;
	private String title;
	private String author;
	private String publisher;
	private int year;
	private int month;
	private int day;
	private float price;
	
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public String execute() {
		String res = "success";
		MySQL sql = new MySQL();
		Vector<Author> authors = sql.selectAuthors(author);
		if (authors.size() == 0) {
			authors = sql.selectAuthors("systemUse");
			res = "fail";
		}
		Book book = new Book();
		Date date = new Date(year-1900, month-1, day);
		book.set(ISBN, title, authors.get(0).getID(), publisher, date, price);
		sql.insertBook(book);
		sql.close();
		return res;
	}
}
