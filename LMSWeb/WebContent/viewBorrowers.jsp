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
		<td><button type="button" class="btn btn-md btn-success" data-toggle="modal" data-target="#myModal1" href="editBorrower.jsp?cardNo=<%=b.getCardNo()%>">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger" onclick="javascript:location.href='deleteBorrower?cardNo=<%=b.getCardNo()%>';">Delete</button></td>
	</tr>
	<%} %>
</table>


<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>