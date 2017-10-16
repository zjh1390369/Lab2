package lab2.model;

import java.sql.Date;

public class Book {
	private String ISBN;
	private String title;
	private int authorid;
	private String publisher;
	private Date publishdate;
	private double price;
	
	public void setISBN(String isbn) {
		this.ISBN = isbn;
	}
	
	public String getISBN() {
		return this.ISBN;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setAuthorID(int authorid) {
		this.authorid = authorid;
	}
	
	public int getAuthorID() {
		return this.authorid;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getPublisher() {
		return this.publisher;
	}
	
	public void setPublishDate(Date date) {
		this.publishdate = date;
	}
	
	public Date getPublishDate() {
		return this.publishdate;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public void set(
			String isbn,
			String title,
			int authorid,
			String publisher,
			Date publishdate,
			double price) {
		this.ISBN = isbn;
		this.title = title;
		this.authorid = authorid;
		this.publisher = publisher;
		this.publishdate = publishdate;
		this.price = price;
	}
	
	public void print() {
		System.out.print(String.format("Book:\n"+
				"    ISBN:       %s\n"+
				"    Title:      %s\n"+
				"    AuthorID:   %d\n"+
				"    Publisher:  %s\n"+
				"    Pulishdate: %s\n"+
				"    Price:      %f\n",
				ISBN, title, authorid, publisher, publishdate.toString(), price));
	}
}
