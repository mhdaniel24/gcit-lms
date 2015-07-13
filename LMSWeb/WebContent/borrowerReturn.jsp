<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.domain.Borrower"%>
<%@page import="com.gcit.lms.domain.BookLoan"%>
<%@page import="java.util.List"%>
<%BorrowerService bs = new BorrowerService();
String cardNo = (String) request.getAttribute("cardNo");
Borrower b = bs.readOneBorrower(Integer.parseInt((String) request.getAttribute("cardNo")));
List<BookLoan> bookLoans = bs.readAllLoansOfBorrower(b);
%>
<%@include file="include.html"%>
<table class="table">

	<tr>
		<th>Book Title</th>
		<th>Branch Name</th>
		<th>Date Out</th>
		<th>Due Date</th>
		<th>Return Book</th>
	</tr>
	<%for(BookLoan bl: bookLoans){ %>
	<tr>
		<td><%out.println(bl.getBook().getTitle()); %></td>
		<td><%out.println(bl.getLibraryBranch().getBranchName()); %></td>
		<td><%out.println(bl.getDateOut()); %></td>
		<td><%out.println(bl.getDueDate()); %></td>
	
		<td><button type="button" class="btn btn-md btn-danger" onclick="javascript:location.href='returnBook?bookId=<%=bl.getBook().getBookId()%>&cardNo=<%=cardNo%>&branchId=<%=bl.getLibraryBranch().getBranchId()%>';">Return</button></td>
	</tr>
	<%} %>
</table>