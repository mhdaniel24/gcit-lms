<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Author"%>
<%AdministrativeService adminService = new AdministrativeService();
	List<Author> authors = adminService.readAllAuthors();
%>
<%@include file="include.html"%>
<script>
	function deleteAuthor(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		
		document.getElementById("authorId").value = id;
		document.deleteFrm.submit();
	}

</script>

<table class="table">

	<tr>
		<th>Author ID</th>
		<th>Author Name</th>
		<th>Edit Author</th>
		<th>Delete Author</th>
	</tr>
	<%for(Author a: authors){ %>
	<tr>
		<td><%out.println(a.getAuthorId()); %></td>
		<td><%out.println(a.getAuthorName()); %></td>
		<td><button type="button" class="btn btn-md btn-success">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger" onclick="javascript:deleteAuthor(<%=a.getAuthorId()%>);">Delete</button></td>
	</tr>
	<%} %>
</table>

<form action="deleteAuthor" method="post" name="deleteFrm">
	<input type="hidden" name="authorId" id="authorId"/>
</form>