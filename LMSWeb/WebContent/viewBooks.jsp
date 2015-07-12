<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%AdministrativeService adminService = new AdministrativeService();
	List<Book> books = adminService.readAllBooks();
%>
<%@include file="include.html"%>

<table class="table">

	<tr>
		<th>Book ID</th>
		<th>Book Title</th>
		<th>Book Publisher</th>
		<th>Edit Book</th>
		<th>Delete Book</th>
	</tr>
	<%for(Book b: books){ %>
	<tr>
		<td><%out.println(b.getBookId());%></td>
		<td><%out.println(b.getTitle());%></td>
		<td><%if(b.getPublisher().getPublisherName() == null){out.println("No Publisher");}else{out.println(b.getPublisher().getPublisherName());}%></td>
		<td><button type="button" class="btn btn-md btn-success" data-toggle="modal" data-target="#myModal1" href="editBook.jsp?bookId=<%=b.getBookId()%>">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger" onclick="javascript:location.href='deleteBook?bookId=<%=b.getBookId()%>';">Delete</button></td>
	</tr>
	<%} %>
</table>

<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>