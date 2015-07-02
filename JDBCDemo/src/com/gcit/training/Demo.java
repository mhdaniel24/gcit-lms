package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Demo {

	public static void main(String[] args) {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
			Statement stmt = conn.createStatement();
			String selectQuery = "select * from tbl_author";
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			while(rs.next())
			{
				System.out.println("Author ID: " + rs.getInt("authorId"));
				System.out.println("Author ID: " + rs.getString("authorName"));
				System.out.println("---------------");
			}
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter a new author: ");
			String authorName = sc.next();
			
			String createQuery = "insert into tbl_author (authorName) values('" + authorName + "')";
			
			stmt.executeUpdate(createQuery);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
