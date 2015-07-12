<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Borrower"%>
<%AdministrativeService adminService = new AdministrativeService();
	List<Borrower> borrowers = adminService.readAllBorrower();
%>
<%@include file="include.html"%>

<table class="table">

	<tr>
		<th>Card Number</th>
		<th>Name</th>
		<th>Address</th>
		<th>Phone</th>
		<th>Edit Borrower</th>
		<th>Delete Borrower</th>
	</tr>
	<%for(Borrower b: borrowers){ %>
	<tr>
		<td><%out.println(b.getCardNo()); %></td>
		<td><%out.println(b.getName()); %></td>
		<td><%out.println(b.getAddress()); %></td>
		<td><%out.println(b.getPhone()); %></td>
		<td><button type="button" class="btn btn-md btn-success">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger" onclick="javascript:location.href='deleteBorrower?cardNo=<%=b.getCardNo()%>';">Delete</button></td>
	</tr>
	<%} %>
</table>
