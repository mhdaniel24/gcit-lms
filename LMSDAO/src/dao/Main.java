package dao;

import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import domain.Author;
import domain.Book;
import domain.BookLoan;
import domain.Borrower;
import domain.LibraryBranch;
import service.ConnectionUtil;

public class Main {

	public static void main(String[] args) {
		printAllAuthors();
		printAllBorrowers();
		printAllBranches();
	}
	
	public static void printAllBranches(){
		ConnectionUtil c = new ConnectionUtil();
		Connection conn;
		try {
			conn = c.createConnection();
			LibraryBranchDAO librarybranch = new LibraryBranchDAO(conn);
			
			List<LibraryBranch> libraryBorrowers = librarybranch.readAll();
			for(LibraryBranch b: libraryBorrowers){
				System.out.println("branch id = " + b.getBranchId() + " branchName = " + b.getBranchName() + " branchAddress = "+ 
						b.getBranchAddress());
				
				HashMap<Book, Integer> map = b.getBookCopies();
				
				for(Entry<Book, Integer> entry : map.entrySet()){
				    System.out.println("\tbookTitle = " + entry.getKey().getTitle() +" numb Copies = "+ entry.getValue());
				}
			}
			System.out.println("-----------------------------------------------------------------------------------");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printAllBorrowers(){
		ConnectionUtil c = new ConnectionUtil();
		Connection conn;
		try {
			conn = c.createConnection();
			BorrowerDAO borrowerdao = new BorrowerDAO(conn);
			
			List<Borrower> borrowers = borrowerdao.readAll();
			for(Borrower b:borrowers){
				System.out.println("borrower id = " + b.getCardNo() + " borrowerName = " + b.getName() + " phone = "+ 
						b.getPhone());
				List<BookLoan> bookLoans = b.getBookLoans();
				
				for(BookLoan bl: bookLoans){
					System.out.println("\tdateOut = " + bl.getDateOut() + " dueDate = " + bl.getDueDate() + " dateIn = " + bl.getDateIn());
				}
				
			}
			System.out.println("-----------------------------------------------------------------------------------");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printAllAuthors(){
		ConnectionUtil c = new ConnectionUtil();
		Connection conn;
		try {
			conn = c.createConnection();
			AuthorDAO authordao = new AuthorDAO(conn);
			
			List<Author> authors = authordao.readAll();
			for(Author a:authors){
				System.out.println("id = " + a.getAuthorId() + " authorName = " + a.getAuthorName());
				List<Book> books = a.getBooks();
				
				for(Book b : books){
					System.out.println("\tbooId = " + b.getBookId() + " title = " + b.getTitle());
				}
				
			}
			System.out.println("-----------------------------------------------------------------------------------");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
