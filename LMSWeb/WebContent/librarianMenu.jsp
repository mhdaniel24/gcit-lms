<%@include file="include.html"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="com.gcit.lms.domain.LibraryBranch"%>
<% String branchId = (String) request.getAttribute("branchId");%>
${result}
<h3>Do you want to:</h3>
<a data-toggle="modal" data-target="#myModal1" href="librarianEditBranch.jsp?branchId=<%=branchId%>">Update the details of the Library</a></td><br />
<a data-toggle="modal" data-target="#myModal1" href="checkcardNo.jsp?action=return">Add copies of Book to the Branch</a></td>


<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
