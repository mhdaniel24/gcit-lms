package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Author;


public class AuthorDAO {
	String driver = "com.mysql.jdbc.Driver";
	String connection = "jdbc:mysql://localhost:3306/library";
	String user = "root";
	String pass = "";
	
	public void create(Author author) throws Exception{
		Connection conn = getConnection();
		
		String insert = "insert into tbl_author (authorName) values(?)";
		PreparedStatement stmt = conn.prepareStatement(insert);
		stmt.setString(1, author.getAuthorName());
		stmt.executeUpdate();

	}

	public void update(Author author) throws Exception{
		Connection conn = getConnection();
		
		String update = "update tbl_author set authorName = ? where authorId = ?";
		PreparedStatement stmt = conn.prepareStatement(update);
		stmt.setString(1, author.getAuthorName());
		stmt.setInt(2, author.getAuthorId());
		stmt.executeUpdate();
	}

	public void delete(Author author) throws Exception{
		Connection conn = getConnection();
		
		String delete = "delete from tbl_author where authorId = ?";
		PreparedStatement stmt = conn.prepareStatement(delete);
		stmt.setInt(1, author.getAuthorId());
		stmt.executeUpdate();
	}

	public List<Author> readAll() throws Exception {
		List<Author> authors =  new ArrayList<Author>(); 
		Connection conn = getConnection();
		String getAllQuery = "SELECT * from tbl_author";
		PreparedStatement stmt = conn.prepareStatement(getAllQuery);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next())
		{
			Author temp = new Author();
			temp.setAuthorId(rs.getInt("authorId"));
			temp.setAuthorName(rs.getString("authorName"));
			authors.add(temp);
		}
		return authors;

	}
	//TODO: Returns one does it need any data?
	public Author readOne() throws Exception{
		Author author = new Author();
		Connection conn = getConnection();
		String getAllQuery = "SELECT * from tbl_author WHERE authorId = ?";
		PreparedStatement stmt = conn.prepareStatement(getAllQuery);
		//stmt.setInteger(1, );
		ResultSet rs = stmt.executeQuery();
		
		return null;
	}
	
	private Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(connection, user, pass);
		return conn;
	}


}
