package com.gcit.lms.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;
import com.gcit.lms.service.AdministrativeService;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/addPublisher", "/viewAuthors", "/deleteAuthor", "/viewPublisher", 
	"/deletePublisher", "/addBorrower", "/viewBorrowers","/deleteBorrower", "/addBook", 
	"/addGenre", "/editAuthor", "/deleteBranch", "/addBranch", "/deleteBook"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		case "/deleteAuthor":
			deleteAuthor(request, response);
			break;
		case "/pageAuthors":
			//pageAuthors(request, response);
			break;
		case "/searchAuthors":
			//searchAuthors(request, response);
			break;
		case "/deleteBorrower": 
			deleteBorrower(request, response);
			break;
		case "/deletePublisher": 
			deletePublisher(request, response);
			break;
		case "/deleteBranch": 
			deleteBranch(request, response);
			break;
		case "/deleteBook": 
			deleteBook(request, response);
			break;
		default:
			break;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		case "/addAuthor":
			createAuthor(request, response);
			break;
		case "viewAuthors":
			viewAuthors(request, response);
			break;
		case "/editAuthor":
			editAuthor(request, response);
			break;
		case "/addPublisher":
			createPublisher(request, response);
			break;
		case "viewPublisher":
			viewPublisher(request, response);
			break;
		case "/addBorrower":
			createBorrower(request, response);
			break;
		case "viewBorrowers":
			viewBorrowers(request, response);
			break;
		case "/addBook":
			createBook(request, response);
			break;
		case "/addGenre":
			createGenre(request, response);
			break;
		case "/addBranch":
			createBranch(request, response);
			break;
		default:
			break;
		}
	}

	private void createAuthor(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authorName = request.getParameter("authorName");
		Author a = new Author();
		a.setAuthorName(authorName);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createAuthor(a);
			request.setAttribute("result", "Author Added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");
		rd.forward(request, response);
	}
	
	private void editAuthor(HttpServletRequest request,
			HttpServletResponse response) {
		String authorName = request.getParameter("authorName");
		int authorId = Integer.parseInt(request.getParameter("authorId"));
		Author a = new Author();
		a.setAuthorName(authorName);
		a.setAuthorId(authorId);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.updateAuthor(a);
			request.setAttribute("result", "Author updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewAuthors.jsp");
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
	
	private void createPublisher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String publisherName = request.getParameter("publisherName");
		String publisherAddress = request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");
		Publisher p = new Publisher();
		p.setPublisherName(publisherName);
		p.setPublisherAddress(publisherAddress);
		p.setPublisherPhone(publisherPhone);
		p.setBooks((List)new ArrayList<Book>());//TODO: check if this step should be taken here or in the Service
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createPublisher(p);
			request.setAttribute("result", "Publisher added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Publisher add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");
		rd.forward(request, response);
	}
	
	private List<Author> viewAuthors(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			return new AdministrativeService().readAllAuthors();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private List<Publisher> viewPublisher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			return new AdministrativeService().readAllPublishers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void deleteAuthor(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authorId = request.getParameter("authorId");
		Author author = new Author();
		author.setAuthorId(Integer.parseInt(authorId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewAuthors.jsp");
		try {
			new AdministrativeService().deleteAuthor(author);

			request.setAttribute("result", "Author Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Author Delete Failed because: " + e.getMessage());
		}
		
		rd.forward(request, response);
	}
	
	private void deletePublisher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String publisherId = request.getParameter("publisherId");
		Publisher publisher = new Publisher();
		publisher.setPublisherId(Integer.parseInt(publisherId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewPublisher.jsp");
		try {
			new AdministrativeService().deletePublisher(publisher);

			request.setAttribute("result", "Publisher Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Publisher Delete Failed because: " + e.getMessage());
		}
		
		rd.forward(request, response);
	}
	
	private void deleteBorrower(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String borrowerId = request.getParameter("cardNo");
		Borrower borrower = new Borrower();
		borrower.setCardNo(Integer.parseInt(borrowerId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBorrowers.jsp");
		try {
			new AdministrativeService().deleteBorrower(borrower);

			request.setAttribute("result", "Borrower Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Borrower Delete Failed because: " + e.getMessage());
		}
		
		rd.forward(request, response);
	}
	
	private List<Borrower> viewBorrowers(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			return new AdministrativeService().readAllBorrower();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void createBorrower(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		Borrower b = new Borrower();
		b.setAddress(address);
		b.setPhone(phone);
		b.setName(name);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createBorrower(b);
			request.setAttribute("result", "Borrower added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Borrower add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");
		rd.forward(request, response);
	}
	
	private void createBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String[] authorIds = request.getParameterValues("authorId");
		String[] genreIds = request.getParameterValues("genreId");
		String[] publisherIds = request.getParameterValues("publisherId");
		
		AdministrativeService adminService = new AdministrativeService();
		Book b = new Book();
		b.setTitle(title);
		
		List<Author> authors = new ArrayList<Author>();
		for(int i = 0; i < authorIds.length; i++){
			try {
				authors.add(adminService.readOneAuthor(Integer.parseInt(authorIds[i])));
			} catch (Exception e1) {
				//report unsuccessful
				e1.printStackTrace();
			}
		}
		b.setAuthors(authors);
		
		List<Genre> genres = new ArrayList<Genre>();
		for(int i = 0; i < genreIds.length; i++){
			try {
				genres.add(adminService.readOneGenre(Integer.parseInt(genreIds[i])));
			} catch (Exception e1) {
				//report unsuccessful
				e1.printStackTrace();
			}
		}
		
		b.setGenres(genres);
		
		try {
			Publisher p = adminService.readOnePublisher(Integer.parseInt(publisherIds[0]));
			//System.out.println(p.getPublisherName());
			b.setPublisher(adminService.readOnePublisher(Integer.parseInt(publisherIds[0])));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			adminService.createBook(b);
			request.setAttribute("result", "Book added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Book add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");
		rd.forward(request, response);
	}
	
	private void createGenre(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String genreName = request.getParameter("genreName");
		Genre g = new Genre();
		g.setGenreName(genreName);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createGenre(g);
			request.setAttribute("result", "Genre Added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Genre add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");
		rd.forward(request, response);
	}
	
	private void deleteBranch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String branchId = request.getParameter("branchId");
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchId(Integer.parseInt(branchId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBranches.jsp");
		try {
			new AdministrativeService().deleteLibraryBranch(branch);

			request.setAttribute("result", "Branch Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Branch Delete Failed because: " + e.getMessage());
		}
		
		rd.forward(request, response);
	}
	
	private void deleteBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = new Book();
		book.setBookId(Integer.parseInt(bookId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBooks.jsp");
		try {
			new AdministrativeService().deleteBook(book);

			request.setAttribute("result", "Book Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Book Delete Failed because: " + e.getMessage());
		}
		
		rd.forward(request, response);
	}
	
	private void createBranch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("branchAddress");
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setBranchAddress(branchAddress);
		libraryBranch.setBranchName(branchName);
		
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createLibraryBranch(libraryBranch);
			request.setAttribute("result", "Library Branch Added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Library Branch add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");
		rd.forward(request, response);
	}
}
