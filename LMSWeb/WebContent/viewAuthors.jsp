<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Author"%>
<%AdministrativeService adminService = new AdministrativeService();
	List<Author> authors = null;
	if (request.getAttribute("authors") != null) {
		authors = (List<Author>) request.getAttribute("authors");
	} else {
		authors = adminService.readAllAuthors();
	}
%>
<%@include file="include.html"%>
<script>
	function searchAuthors(){
		$.ajax({
			  url: "searchAuthors",
			  data: {
				  searchString: $('#searchString').val()
			  }
			}).done(function(data) {
				$('#authorsTable').html(data);
			});
	}
</script>
${result}

<form action="searchAuthors" method="post">
	<input type="text" class="col-md-8" id="searchString" name="searchString"
		placeholder="Enter title name to search"><input type="button"
		value="Search!" onclick="javascript:searchAuthors();">
</form>

<table class="table" id="authorsTable">

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
		<td><button type="button" class="btn btn-md btn-success" data-toggle="modal" data-target="#myModal1" href="editAuthor.jsp?authorId=<%=a.getAuthorId()%>">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger" onclick="javascript:location.href='deleteAuthor?authorId=<%=a.getAuthorId()%>';">Delete</button></td>
	</tr>
	<%} %>
</table>


<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>