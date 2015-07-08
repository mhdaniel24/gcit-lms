<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%AdministrativeService adminService = new AdministrativeService();
	List<Publisher> publishers = adminService.readAllPublishers();
%>
<%@include file="include.html"%>
<script>
	function deletePublisher(id) {
		//document.location.href = "deletePublisher?publisherId="+id;
		
		document.getElementById("publisherId").value = id;
		document.deleteFrm.submit();
	}

</script>

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
		<td><button type="button" class="btn btn-md btn-success">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger" onclick="javascript:deletePublisher(<%=p.getPublisherId()%>);">Delete</button></td>
	</tr>
	<%} %>
</table>

<form action="deletePublisher" method="post" name="deleteFrm">
	<input type="hidden" name="publisherId" id="publisherId"/>
</form>