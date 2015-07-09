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
				<td><select type="text" name="author">
				<%for(int i = 0; i < authors.size(); i++){ %>
						<option><%out.println(authors.get(i).getAuthorId() +" "+authors.get(i).getAuthorName());%></option>
				<%} %>
				</select></td>
			</tr>
			<tr>
				<td>Select the genres:</td>
				<td><select>
				<%for(int i = 0; i < genres.size(); i++){ %>
						<option><%out.println(genres.get(i).getGenreName());%></option>
				<%} %>
				</select></td>
			</tr>
			<tr>
				<td>Select the Publisher</td>
				<td><select>
				<%for(int i = 0; i < publishers.size(); i++){ %>
						<option><%out.println(publishers.get(i).getPublisherName());%></option>
				<%} %>
				</select></td>
			</tr>
		</table>
		<input type="submit">
	</body>
</form>
