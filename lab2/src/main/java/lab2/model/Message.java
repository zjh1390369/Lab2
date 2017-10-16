package lab2.model;

public class Message {
	private Book book;
	private Author author;
	
	public Book getBook() {
		return this.book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public Author getAuthor() {
		return this.author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public boolean equal(Message mes) {
		return this.book.getISBN().equals(mes.getBook().getISBN());
	}
}
