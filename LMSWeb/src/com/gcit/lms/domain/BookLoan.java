package com.gcit.lms.domain;

import java.sql.Timestamp;

public class BookLoan {
	Timestamp dateOut;
	Timestamp dateIn;
	Timestamp dueDate;
	
	private Book book;
	private LibraryBranch libraryBranch;
	private Borrower borrower;
	/**
	 * @return the dateOut
	 */
	public Timestamp getDateOut() {
		return dateOut;
	}
	/**
	 * @param dateOut the dateOut to set
	 */
	public void setDateOut(Timestamp dateOut) {
		this.dateOut = dateOut;
	}
	/**
	 * @return the dateIn
	 */
	public Timestamp getDateIn() {
		return dateIn;
	}
	/**
	 * @param dateIn the dateIn to set
	 */
	public void setDateIn(Timestamp dateIn) {
		this.dateIn = dateIn;
	}
	/**
	 * @return the dueDate
	 */
	public Timestamp getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}
	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}
	/**
	 * @return the libraryBranch
	 */
	public LibraryBranch getLibraryBranch() {
		return libraryBranch;
	}
	/**
	 * @param libraryBranch the libraryBranch to set
	 */
	public void setLibraryBranch(LibraryBranch libraryBranch) {
		this.libraryBranch = libraryBranch;
	}
	/**
	 * @return the borrower
	 */
	public Borrower getBorrower() {
		return borrower;
	}
	/**
	 * @param borrower the borrower to set
	 */
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}
	
	
	
}
