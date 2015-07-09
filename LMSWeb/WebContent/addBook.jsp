<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Author"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%@page import="com.gcit.lms.domain.Genre"%>
<%@include file="include.html"%>
<%AdministrativeService adminService = new AdministrativeService();
	List<Author> authors = adminService.readAllAuthors();
	List<Genre> genres = adminService.readAllGenres();
	List<Publisher> publishers = adminService.readAllPublishers();
%>

<form action="addBook" method="post">
	<body>
		<h2>Hello Admin - Enter Book Details</h2>
		<table class="table">
			<tr>
				<td>Enter Book Name:</td>
				<td><input type="text" name="title" /></td>
			</tr>
			<tr>
				<td>Select the authors:</td>
				<td><select multiple="multiple" name="authorId">
				<%for(Author a : authors){ %>
						<option value=<%=a.getAuthorId() %>><%out.println(a.getAuthorName());%></option>
				<%} %>
				</select></td>
			</tr>
			<tr>
				<td>Select the genres:</td>
				<td><select multiple="multiple" name="genreId">
				<%for(Genre g : genres){ %>
						<option value=<%=g.getGenreId()%>><%out.println(g.getGenreName());%></option>
				<%} %>
				</select></td>
			</tr>
			<tr>
				<td>Select the Publisher</td>
				<td><select name="publisherId">
				<%for(Publisher p: publishers){ %>
						<option value=<%=p.getPublisherId()%>><%out.println(p.getPublisherName());%></option>
				<%} %>
				</select></td>
			</tr>
		</table>
		<input type="submit">
	</body>
</form>
