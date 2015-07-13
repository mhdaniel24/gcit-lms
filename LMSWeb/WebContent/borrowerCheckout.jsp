<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.domain.Borrower"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%@page import="java.util.List"%>
<%BorrowerService bs = new BorrowerService();
//Borrower b = bs.readOneBorrower(Integer.parseInt((String) request.getAttribute("cardNo")));
String cardNo = (String) request.getAttribute("cardNo");
String branchId = (String) request.getAttribute("branchId");
List<Book> books = bs.getBooksToBeBorrowedInBranch(bs.readOneLibraryBranch(Integer.parseInt(branchId)));
%>
<%@include file="include.html"%>

<table class="table">

	<tr>
		<th>Book Title</th>
		<th>Check Out</th>
	</tr>
	<%for(Book b: books){ %>
	<tr>
		<td><%out.println(b.getTitle()); %></td>
		<td><button type="button" class="btn btn-md btn-success" onclick="javascript:location.href='checkOutBook?bookId=<%=b.getBookId()%>&branchId=<%=branchId%>&cardNo=<%=cardNo%>';">Check Out</button></td>
	</tr>
	<%} %>
</table>