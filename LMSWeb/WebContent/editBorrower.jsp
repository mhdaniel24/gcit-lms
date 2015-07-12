<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Borrower"%>
<%AdministrativeService adminService = new AdministrativeService();
 String cardNo = request.getParameter("cardNo");
 Borrower borrower = adminService.readOneBorrower(Integer.parseInt(cardNo));
%>
<div class="modal-body">
	<form action="editBorrower" method="post">

		<table class="table">

			<tr>
				<th>Enter Borower Name: <input type="text" name="name"
					value=<%=borrower.getName()%>></th>
			</tr>

			<tr>
				<th>Enter Borower Address: <input type="text" name="address"
					value=<%=borrower.getAddress()%>></th>
			</tr>
			<tr>
				<th>Enter Borower phone: <input type="text" name="phone"
					value=<%=borrower.getPhone()%>></th>
			</tr>
		</table>

		<input type="hidden" name="cardNo" value=<%=borrower.getCardNo()%>>
		<input type="submit" />
	</form>
</div>

