package lab2.sql;

import java.sql.*;
import java.util.Vector;

import lab2.model.*;

public class MySQL {
	private final String driver = "com.mysql.jdbc.Driver";;
	private final String url = "jdbc:mysql://eaizakjhhwpv.mysql.sae.sina.com.cn:10641/bookdb?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT";
	private final String user = "root";
	private final String password = "123456";
	
	private Connection con = null;
	private Statement stm = null;
	private ResultSet res = null;
	
	public MySQL() {
		try {
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url, user, password);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertBook(Book book) {
		try {
			stm = con.createStatement();
			String sql = "INSERT INTO Book (ISBN, Title, AuthorID, Publisher, PublishDate, Price) VALUES " +
					String.format("(\"%s\", \"%s\", %d, \"%s\", '%s', %f);",
							book.getISBN(), book.getTitle(), book.getAuthorID(), book.getPublisher(), book.getPublishDate(), book.getPrice());
			stm.executeUpdate(sql);
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteBook(String isbn) {
		try {
			stm = con.createStatement();
			String sql = "DELETE FROM Book WHERE " +
					String.format("ISBN = '%s';", isbn);
			stm.executeUpdate(sql);
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteBook(Book book) {
		deleteBook(book.getISBN());
	}
	
	public void executeUpdata(String str) {
		try {
			stm = con.createStatement();
			stm.executeUpdate(str);
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Book selectBook(String isbn) {
		Book book = null;
		try {
			stm = con.createStatement();
			String sql = String.format("SELECT * FROM Book WHERE ISBN = '%s';", isbn);
			res = stm.executeQuery(sql);
			if (res.next()) {
				book = new Book();
				book.setAuthorID(res.getInt("AuthorID"));
				book.setISBN(isbn);
				book.setPrice(res.getFloat("Price"));
				book.setPublishDate(res.getDate("PublishDate"));
				book.setPublisher(res.getString("Publisher"));
				book.setTitle(res.getString("Title"));
			}
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}
	
	public Vector<Book> selectBooks(int authorid) {
		Vector<Book> ret = new Vector<Book>();
		try {
			stm = con.createStatement();
			String sql = String.format("SELECT * FROM Book WHERE AuthorID = %d;", authorid);
			res = stm.executeQuery(sql);
			while (res.next()) {
				Book book = new Book();
				book.setAuthorID(res.getInt("AuthorID"));
				book.setISBN(res.getString("ISBN"));
				book.setPrice(res.getFloat("Price"));
				book.setPublishDate(res.getDate("PublishDate"));
				book.setPublisher(res.getString("Publisher"));
				book.setTitle(res.getString("Title"));
				ret.add(book);
			}
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public Vector<Book> selectBooksByTitle(String title) {
		Vector<Book> ret = new Vector<Book>();
		try {
			stm = con.createStatement();
			String sql = String.format("SELECT * FROM Book WHERE Title LIKE '%%%s%%';", title);
			res = stm.executeQuery(sql);
			while (res.next()) {
				Book book = new Book();
				book.setAuthorID(res.getInt("AuthorID"));
				book.setISBN(res.getString("ISBN"));
				book.setPrice(res.getFloat("Price"));
				book.setPublishDate(res.getDate("PublishDate"));
				book.setPublisher(res.getString("Publisher"));
				book.setTitle(res.getString("Title"));
				ret.add(book);
			}
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public Vector<Book> selectBooks(String name) {
		Vector<Author> authors = selectAuthors(name);
		Vector<Book> ret = new Vector<Book>();
		for (Author author : authors) {
			ret.addAll(selectBooks(author.getID()));
		}
		return ret;
	}
	
	public Vector<Book> select(String text) {
		Vector<Book> books = new Vector<Book>();
		if (selectBook(text) != null)
			books.add(selectBook(text));
		books.addAll(selectBooks(text));
		books.addAll(selectBooksByTitle(text));
		return books;
	}
	
	public void update(Book book) {
		try {
			stm = con.createStatement();
			String sql = "UPDATE Book SET " +
					String.format("Title = '%s', AuthorID = %d, Publisher = '%s', PublishDate = '%s', Price = %f ",
							book.getTitle(), book.getAuthorID(), book.getPublisher(), book.getPublishDate(), book.getPrice()) +
					String.format("WHERE ISBN = '%s';", book.getISBN());
			stm.executeUpdate(sql);
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertAuthor(Author author) {
		try {
			stm = con.createStatement();
			String sql = "INSERT INTO Author (Name, Age, Country) VALUES " +
					String.format("(\"%s\", %d, \"%s\");",
							author.getName(), author.getAge(), author.getCountry());
			stm.executeUpdate(sql);
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<Author> selectAuthors(String name) {
		Vector<Author> ret = new Vector<Author>();
		try {
			stm = con.createStatement();
			String sql = String.format("SELECT * FROM Author WHERE Name Like '%%%s%%';", name);
			res = stm.executeQuery(sql);
			while (res.next()) {
				Author author = new Author();
				author.setID(res.getInt("AuthorID"));
				author.setAge(res.getInt("Age"));
				author.setCountry(res.getString("Country"));
				author.setName(res.getString("Name"));
				ret.add(author);
			}
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public Author selectAuthor(int authorid) {
		Author author = null;
		try {
			stm = con.createStatement();
			String sql = String.format("SELECT * FROM Author WHERE AuthorID = %d;", authorid);
			res = stm.executeQuery(sql);
			if (res.next()) {
				author = new Author();
				author.setID(res.getInt("AuthorID"));
				author.setAge(res.getInt("Age"));
				author.setCountry(res.getString("Country"));
				author.setName(res.getString("Name"));
			}
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return author;
	}
	
	public void close() {
		try {
			if (con != null) con.close();
			if (stm != null) stm.close();
			if (res != null) res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MySQL test = new MySQL();
		test.executeUpdata("CREATE TABLE `Book` (\n" + 
				"  `ISBN` char(13) NOT NULL,\n" + 
				"  `Title` varchar(40) NOT NULL,\n" + 
				"  `AuthorID` int(11) DEFAULT NULL,\n" + 
				"  `Publisher` varchar(40) DEFAULT NULL,\n" + 
				"  `PublishDate` date DEFAULT NULL,\n" + 
				"  `Price` float DEFAULT NULL,\n" + 
				"  PRIMARY KEY (`ISBN`),\n" + 
				"  KEY `AuthorID` (`AuthorID`)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		//Book book = new Book();
		//Date date = new Date(2002-1900,5,1);
		//book.set("9780066620732", "Just For Fun", 1, "HarperBusiness", date, 14.99);
		//test.insertBook(book);
		//test.deleteBook(book.getISBN());
		//test.update(book);
		//book = test.selectBook("9780066620732");
		//for (Author x: test.selectAuthors("Linus")) {
		//	x.print();
		//}
		//for (Book x : test.selectBooks("Linus Benedict Torvalds"))
		//	x.print();
		//for (Book x : test.selectBooks(1))
		//	x.print();
		//for (Book x : test.select("Just For Fun"))
		//	x.print();
		//test.selectAuthor(1).print();
		//String ISBN = "9780066620732";
		//Book book;
		//Author authorx;
		//MySQL sql = new MySQL();
		//book = sql.selectBook(ISBN);
		//authorx = sql.selectAuthor(book.getAuthorID());
		//book.print();
		//authorx.print();
		//sql.close();
		//return;
		
		test.close();
	}
}

