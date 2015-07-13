<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.LibraryBranch"%>
<%LibrarianService libService = new LibrarianService();
 String branchId = request.getParameter("branchId");
 LibraryBranch lb = libService.readOneLibraryBranch(Integer.parseInt(branchId));
%>
<div class="modal-body">
	<form action="librarianEditBranch" method="post">

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

