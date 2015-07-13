<%@include file="include.html"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="com.gcit.lms.domain.LibraryBranch"%>
<%LibrarianService librarianService = new LibrarianService();
	List<LibraryBranch> branches = librarianService.readAllLibraryBranchs();
%>

<h2>Hello Librarian - Welcome to GCIT Library Management System</h2>
${result}
<h3>Select the branch you work for:</h3>







<table class="table">

	<tr>
		<th>Branch Name</th>
		<th>Branch Address</th>
		<th>Select</th>
	</tr>
	<%for(LibraryBranch b: branches){ %>
	<tr>
		<td><%out.println(b.getBranchName()); %></td>
		<td><%out.println(b.getBranchAddress()); %></td>
		<td><button type="button" class="btn btn-md btn-success" onclick="javascript:location.href='librarianBranchSelected?branchId=<%=b.getBranchId()%>';">Select</button></td>
	</tr>
	<%} %>
</table>


<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
