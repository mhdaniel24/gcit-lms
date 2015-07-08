<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Borrower"%>
<%AdministrativeService adminService = new AdministrativeService();
	List<Borrower> borrowers = adminService.readAllBorrower();
%>
<%@include file="include.html"%>
<script>
	function deleteBorrower(id) {
		//document.location.href = "deletePublisher?publisherId="+id;
		
		document.getElementById("cardNo").value = id;
		document.deleteFrm.submit();
	}

</script>

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
		<td><button type="button" class="btn btn-md btn-danger" onclick="javascript:deleteBorrower(<%=b.getCardNo()%>);">Delete</button></td>
	</tr>
	<%} %>
</table>

<form action="deleteBorrower" method="post" name="deleteFrm">
	<input type="hidden" name="cardNo" id="cardNo"/>
</form>