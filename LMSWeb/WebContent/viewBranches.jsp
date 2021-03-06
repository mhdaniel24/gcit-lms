<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.LibraryBranch"%>
<%AdministrativeService adminService = new AdministrativeService();
	List<LibraryBranch> branches = adminService.readAllLibraryBranchs();
%>
<%@include file="include.html"%>

<table class="table">

	<tr>
		<th>Branch ID</th>
		<th>Branch Name</th>
		<th>Branch Address</th>
		<th>Edit Branch</th>
		<th>Delete Branch</th>
	</tr>
	<%for(LibraryBranch b: branches){ %>
	<tr>
		<td><%out.println(b.getBranchId()); %></td>
		<td><%out.println(b.getBranchName()); %></td>
		<td><%out.println(b.getBranchAddress()); %></td>
		<td><button type="button" class="btn btn-md btn-success" data-toggle="modal" data-target="#myModal1" href="editBranch.jsp?branchId=<%=b.getBranchId()%>">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger" onclick="javascript:location.href='deleteBranch?branchId=<%=b.getBranchId()%>';">Delete</button></td>
	</tr>
	<%} %>
</table>

<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>