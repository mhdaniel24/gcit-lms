<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%AdministrativeService adminService = new AdministrativeService();
	List<Publisher> publishers = adminService.readAllPublishers();
%>
<%@include file="include.html"%>

<table class="table">

	<tr>
		<th>Publisher ID</th>
		<th>Publisher Name</th>
		<th>Publisher Address</th>
		<th>Publisher Phone</th>
		<th>Edit Publisher</th>
		<th>Delete Publisher</th>
	</tr>
	<%for(Publisher p: publishers){ %>
	<tr>
		<td><%out.println(p.getPublisherId()); %></td>
		<td><%out.println(p.getPublisherName()); %></td>
		<td><%out.println(p.getPublisherAddress()); %></td>
		<td><%out.println(p.getPublisherPhone()); %></td>
		<td><button type="button" class="btn btn-md btn-success" data-toggle="modal" data-target="#myModal1" href="editPublisher.jsp?publisherId=<%=p.getPublisherId()%>">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger" onclick="javascript:location.href='deletePublisher?publisherId=<%=p.getPublisherId()%>';">Delete</button></td>
	</tr>
	<%} %>
</table>

<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>