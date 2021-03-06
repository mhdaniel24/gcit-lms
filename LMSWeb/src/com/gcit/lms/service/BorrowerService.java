package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gcit.lms.domain.*;
import com.gcit.lms.dao.*;

public class BorrowerService {
	
	public List<LibraryBranch> readAllLibraryBranchs() throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			List<LibraryBranch> allLibraryBranches = lbdao.readAll();
			conn.commit();//not sure if needed
			return allLibraryBranches;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}
	
	public void checkOutBook(LibraryBranch branch, Book b, Borrower bo) throws Exception{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		BorrowerDAO borrowerdao = new BorrowerDAO(conn);
		BookLoanDAO loandao = new BookLoanDAO(conn);
		
		try{
			LibraryBranch libBranch = lbdao.readOne(branch.getBranchId());

			if(libBranch == null){
				throw new Exception(
						"The branch id provided does not match any branch");
			}

			Book book = null;
			for(Entry<Book, Integer> entry : libBranch.getBookCopies().entrySet()){
				if(entry.getKey().getBookId() == b.getBookId()){
					book = entry.getKey();
					break;
				}
			}

			if(book != null && libBranch.getBookCopies().get(book) > 0){
				
				Borrower borrower = borrowerdao.readOne(bo.getCardNo());
				if(borrower == null){
					throw new Exception(
							"The  card number provided does not match any borrower");
				}

				
				if(loandao.readOne(b.getBookId(), bo.getCardNo(), branch.getBranchId()) != null){
					throw new Exception(
							"This borrower already borrow this book in this library branch");
				}
			
				BookLoan bookLoan = new BookLoan();
				bookLoan.setBook(book);
				bookLoan.setBorrower(borrower);
				bookLoan.setLibraryBranch(libBranch);
				bookLoan.setDateOut(new Timestamp(System.currentTimeMillis()));
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_YEAR, 7);
				bookLoan.setDueDate(new Timestamp(cal.getTimeInMillis()));
				loandao.create(bookLoan);
				

				BookCopiesDAO bcdao = new BookCopiesDAO(conn);
				BookCopies bc = new BookCopies();
				bc.setBookId(book.getBookId());
				bc.setBranchId(libBranch.getBranchId());
				bc.setNoOfCopies(libBranch.getBookCopies().get(book) - 1);
				bcdao.update(bc);

				conn.commit();
			}else{
				throw new Exception(
						"In order for a book to be checkout it must have more than 1 copy in the library branch");
			}
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
		} finally {
			conn.close();
		}
	}
	
	public void returnBook(LibraryBranch br, Book bo, Borrower bor) throws Exception{
		//make sure that the book id actually maps a book
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		BookDAO bdao = new BookDAO(conn);
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		BorrowerDAO borrowerdao = new BorrowerDAO(conn);
		BookLoanDAO loandao = new BookLoanDAO(conn);
		
		try{
			LibraryBranch libBranch = lbdao.readOne(br.getBranchId());
			if(libBranch == null){
				throw new Exception(
						"The provided branch id does not match any branch");
			}

			Book book = bdao.readOne(bo.getBookId());
			if(book == null){
				throw new Exception(
						"The provided book id does not match any book");
			}

			Borrower borrower = borrowerdao.readOne(bor.getCardNo());
			if(borrower == null){
				throw new Exception(
						"The provided cardNo does not match any borrower");
			}
			if(loandao.readOne(bo.getBookId(), bor.getCardNo(), br.getBranchId()) == null){
				throw new Exception(
						"This borrower has not borrow this book in this library branch");
			}else{
				BookLoan bl = new BookLoan();
				bl.setBook(bdao.readOne(bo.getBookId()));
				bl.setLibraryBranch(lbdao.readOne(br.getBranchId()));
				bl.setBorrower(borrowerdao.readOne(bor.getCardNo()));
				loandao.delete(bl);
			}
			
			
			BookCopies bc = new BookCopies();
			bc.setBookId(book.getBookId());
			bc.setBranchId(libBranch.getBranchId());
			
			boolean flag = false;
			for(Entry<Book, Integer> entry : libBranch.getBookCopies().entrySet()){
				if(entry.getKey().getBookId() == bo.getBookId()){
					bc.setNoOfCopies(entry.getValue() + 1);
					bcdao.update(bc);
					flag = true;
				}
			}

			if(!flag){
				bc.setNoOfCopies(1);
				bcdao.create(bc);
			}

			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
		} finally {
			conn.close();
		}
	}
	
	public Borrower readOneBorrower(int borrowerId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			Borrower borrower = bdao.readOne(borrowerId);
			conn.commit();//not sure if needed
			return borrower;
		} catch (Exception e) {//not a valid bookId
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}
	
	public List<BookLoan> readAllLoansOfBorrower(Borrower borrower) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			BookLoanDAO bookLoandao = new BookLoanDAO(conn);
			BorrowerDAO borrowerdao = new BorrowerDAO(conn);
			BookDAO bookdao = new BookDAO(conn);
			
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			
			List<BookLoan> allLoans = bookLoandao.readAll();
			List<BookLoan> borrowerLoans = new ArrayList<BookLoan>();
			for(BookLoan bl : allLoans){
				if(bl.getBorrower().getCardNo() == borrower.getCardNo()){
					borrowerLoans.add(bl);
				}
			}
			conn.commit();//not sure if needed
			return borrowerLoans;
		} catch (Exception e) {//not a valid bookId
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}
	public List<Book> getBooksToBeBorrowedInBranch(LibraryBranch lb){
		List<Book> books = new ArrayList<Book>();
		for(Map.Entry<Book, Integer> entry: lb.getBookCopies().entrySet()){
			if(entry.getValue() > 0){
				books.add(entry.getKey());
			}
		}
		return books;
		
	}
	
	public LibraryBranch readOneLibraryBranch(int branchId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			LibraryBranch libraryBranch = lbdao.readOne(branchId);
			conn.commit();//not sure if needed
			return libraryBranch;
		} catch (Exception e) {//not a valid bookId
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}
	
	
	
}
