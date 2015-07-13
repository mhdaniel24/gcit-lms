<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%LibrarianService ls = new LibrarianService();
List<Book> books = ls.readAllBooks();
String branchId = (String) request.getAttribute("branchId");
%>

<%@include file="include.html"%>

<table class="table">
	<tr>
		<th>Book Title</th>
		<th>Select Book To Add Copies</th>
	</tr>
	<%for(Book b: books){ %>
	<tr>
		<td><%out.println(b.getTitle()); %></td>
		<td><button type="button" class="btn btn-md btn-success" data-toggle="modal" data-target="#myModal1" href="changeNumbCopies.jsp?bookId=<%=b.getBookId()%>&branchId=<%=branchId%>">Select</button></td>
	</tr>
	<%} %>
</table>


<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>