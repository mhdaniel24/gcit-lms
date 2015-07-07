package service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map.Entry;

import domain.Author;
import domain.Book;
import domain.BookLoan;
import domain.Borrower;
import domain.Genre;
import domain.LibraryBranch;
import domain.Publisher;

import java.sql.Timestamp;
public class Main {

	public static void main(String[] args) {
		
		
		AdministrativeService as = new AdministrativeService();
		try {
			Borrower b = as.readOneBorrower(8);
			as.deleteBorrower(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
