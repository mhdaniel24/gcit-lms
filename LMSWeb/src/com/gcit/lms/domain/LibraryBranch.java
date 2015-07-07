package com.gcit.lms.domain;

import java.util.HashMap;
import java.util.List;

public class LibraryBranch {
	private int branchId;
	private String branchName;
	private String branchAddress;
	
	private HashMap<Book, Integer> bookCopies;
	private List<BookLoan> loans;
	/**
	 * @return the loans
	 */
	public List<BookLoan> getLoans() {
		return loans;
	}
	/**
	 * @param loans the loans to set
	 */
	public void setLoans(List<BookLoan> loans) {
		this.loans = loans;
	}
	/**
	 * @return the branchId
	 */
	public int getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * @return the branchAddress
	 */
	public String getBranchAddress() {
		return branchAddress;
	}
	/**
	 * @param branchAddress the branchAddress to set
	 */
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	/**
	 * @return the bookCopies
	 */
	public HashMap<Book, Integer> getBookCopies() {
		return bookCopies;
	}
	/**
	 * @param bookCopies the bookCopies to set
	 */
	public void setBookCopies(HashMap<Book, Integer> bookCopies) {
		this.bookCopies = bookCopies;
	}
	
	
}
