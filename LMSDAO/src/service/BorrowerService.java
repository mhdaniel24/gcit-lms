package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map.Entry;

import dao.BookCopiesDAO;
import dao.BookDAO;
import dao.BookLoanDAO;
import dao.BorrowerDAO;
import dao.LibraryBranchDAO;
import domain.Book;
import domain.BookCopies;
import domain.BookLoan;
import domain.Borrower;
import domain.LibraryBranch;

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
	
	public void checkOutBook(int branchId, int bookId, int cardNo) throws Exception{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		BorrowerDAO borrowerdao = new BorrowerDAO(conn);
		BookLoanDAO loandao = new BookLoanDAO(conn);
		
		try{
			LibraryBranch libBranch = lbdao.readOne(branchId);

			if(libBranch == null){
				throw new Exception(
						"The branch id provided does not match any branch");
			}

			Book book = null;
			for(Entry<Book, Integer> entry : libBranch.getBookCopies().entrySet()){
				if(entry.getKey().getBookId() == bookId){
					book = entry.getKey();
					break;
				}
			}

			if(book != null && libBranch.getBookCopies().get(book) > 0){
				
				Borrower borrower = borrowerdao.readOne(cardNo);
				if(borrower == null){
					throw new Exception(
							"The  card number provided does not match any borrower");
				}

				
				if(loandao.readOne(bookId, cardNo, branchId) != null){
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
	
	public void returnBook(int branchId, int bookId, int cardNo) throws Exception{
		//make sure that the book id actually maps a book
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		BookDAO bdao = new BookDAO(conn);
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		BorrowerDAO borrowerdao = new BorrowerDAO(conn);
		BookLoanDAO loandao = new BookLoanDAO(conn);
		
		try{
			LibraryBranch libBranch = lbdao.readOne(branchId);
			if(libBranch == null){
				throw new Exception(
						"The provided branch id does not match any branch");
			}

			Book book = bdao.readOne(bookId);
			if(book == null){
				throw new Exception(
						"The provided book id does not match any book");
			}

			Borrower borrower = borrowerdao.readOne(cardNo);
			if(borrower == null){
				throw new Exception(
						"The provided cardNo does not match any borrower");
			}
			if(loandao.readOne(bookId, cardNo, branchId) == null){
				throw new Exception(
						"This borrower has not borrow this book in this library branch");
			}else{
				BookLoan bl = new BookLoan();
				bl.setBook(bdao.readOne(bookId));
				bl.setLibraryBranch(lbdao.readOne(branchId));
				bl.setBorrower(borrowerdao.readOne(cardNo));
				loandao.delete(bl);
			}
			
			
			BookCopies bc = new BookCopies();
			bc.setBookId(book.getBookId());
			bc.setBranchId(libBranch.getBranchId());
			
			boolean flag = false;
			for(Entry<Book, Integer> entry : libBranch.getBookCopies().entrySet()){
				if(entry.getKey().getBookId() == bookId){
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
	
}
