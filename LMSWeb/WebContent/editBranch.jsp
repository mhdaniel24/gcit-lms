<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.LibraryBranch"%>
<%AdministrativeService adminService = new AdministrativeService();
 String branchId = request.getParameter("branchId");
 LibraryBranch lb = adminService.readOneLibraryBranch(Integer.parseInt(branchId));
%>
<div class="modal-body">
	<form action="editBranch" method="post">

		<table class="table">

			<tr>
				<th>Enter Branch Name: <input type="text" name="branchName"
					value=<%=lb.getBranchName()%>></th>
			</tr>

			<tr>
				<th>Enter Branch Address: <input type="text" name="branchAddress"
					value=<%=lb.getBranchAddress()%>></th>
			</tr>
			
		</table>

		<input type="hidden" name="branchId" value=<%=lb.getBranchId()%>>
		<input type="submit" />
	</form>
</div>

