package com.gcit.lms.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.service.BorrowerService;

/**
 * Servlet implementation class BorrowerServlet
 */
@WebServlet({"/checkcardNo", "/returnBook", "/selectABranch", "/checkOutBook"})
public class BorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		case "/returnBook":
			returnBook(request, response);
			break;
		case "/selectABranch":
			//process it
			selectBranch(request,response);
			break;
		case "/checkOutBook":
			checkOutBook(request, response);
			break;
		default:
			break;
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		case "/checkcardNo":
			checkCardNo(request, response);
			break;
		case "/selectABranch":
			//process it
			break;
		default:
			break;
		}
	}
	
	private void checkCardNo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String action = request.getParameter("action");
		String cardNo = request.getParameter("cardNo");
		BorrowerService borrowerService = new BorrowerService();
		RequestDispatcher rd;
		try {
			Borrower b = borrowerService.readOneBorrower(Integer.parseInt(cardNo));
			if(b == null){
				rd = getServletContext().getRequestDispatcher("/borrower.jsp");
				request.setAttribute("result","Card Number is not valid");
			}else{
				request.setAttribute("cardNo",cardNo);
				if(action.equals("return")){
					rd = getServletContext().getRequestDispatcher("/borrowerReturn.jsp");
				}else{
					rd = getServletContext().getRequestDispatcher("/selectBranch.jsp");
				}
			}
		} catch (Exception e) {
			rd = getServletContext().getRequestDispatcher("/borrower.jsp");
			request.setAttribute("result","Error verifying card number " + e.getMessage());
		}
		
		
		rd.forward(request, response);
			 
	}
	
	private void returnBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String branchId = request.getParameter("branchId");
		String cardNo = request.getParameter("cardNo");
		String bookId = request.getParameter("bookId");
		
		Book book = new Book();
		book.setBookId(Integer.parseInt(bookId));
		
		Borrower borrower = new Borrower();
		borrower.setCardNo(Integer.parseInt(cardNo));
		
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchId(Integer.parseInt(branchId));
		
		BorrowerService br = new BorrowerService();
		
		RequestDispatcher rd =getServletContext().getRequestDispatcher("/borrowerReturn.jsp");
		request.setAttribute("cardNo",cardNo);
		try {
			br.returnBook(lb, book, borrower);
		} catch (Exception e) {
			e.printStackTrace();
		}
		rd.forward(request, response);
	}
	
	private void selectBranch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String branchId = request.getParameter("branchId");
		String cardNo = request.getParameter("cardNo");
		System.out.println(branchId);
		System.out.println(cardNo);
		

		RequestDispatcher rd =getServletContext().getRequestDispatcher("/borrowerCheckout.jsp");
		request.setAttribute("cardNo",cardNo);
		request.setAttribute("branchId", branchId);
		rd.forward(request, response);
	}
	
	private void checkOutBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String branchId = request.getParameter("branchId");
		String cardNo = request.getParameter("cardNo");
		String bookId = request.getParameter("bookId");
		
		System.out.println(branchId + " " + cardNo+" "+bookId);
		
		Book book = new Book();
		book.setBookId(Integer.parseInt(bookId));
		
		Borrower borrower = new Borrower();
		borrower.setCardNo(Integer.parseInt(cardNo));
		
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchId(Integer.parseInt(branchId));
		
		BorrowerService br = new BorrowerService();
		
		RequestDispatcher rd =getServletContext().getRequestDispatcher("/borrower.jsp");
		request.setAttribute("result","Book checked out succesfully");
		try {
			br.checkOutBook(lb, book, borrower);
		} catch (Exception e) {
			e.printStackTrace();
		}
		rd.forward(request, response);
	}

}
