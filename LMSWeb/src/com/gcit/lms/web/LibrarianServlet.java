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
import com.gcit.lms.service.AdministrativeService;
import com.gcit.lms.service.BorrowerService;

/**
 * Servlet implementation class LibrarianServlet
 */
@WebServlet({"/librarianBranchSelected", "/librarianEditBranch"})
public class LibrarianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LibrarianServlet() {
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
		case "/librarianBranchSelected":
			librarianBranchSelected(request, response);
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
		case "/librarianEditBranch":
			System.out.println("Hereeeeeeeeee");
			librarianEditBranch(request, response);
		default:
			break;
		}
	}

	private void librarianBranchSelected(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String branchId = request.getParameter("branchId");
		RequestDispatcher rd =getServletContext().getRequestDispatcher("/librarianMenu.jsp");
		request.setAttribute("branchId",branchId);
		rd.forward(request, response);
	}
	private void librarianEditBranch(HttpServletRequest request,
			HttpServletResponse response) {
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("branchAddress");
		
		
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchAddress(branchAddress);
		lb.setBranchId(branchId);
		lb.setBranchName(branchName);
		
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.updateLibraryBranch(lb);
			request.setAttribute("result", "Library Branch updated Successfully");
			request.setAttribute("branchId", Integer.toString(branchId));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Library Branch update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/librarianMenu.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
