<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.LibraryBranch"%>
<%BorrowerService bs = new BorrowerService();
List<LibraryBranch> branches = bs.readAllLibraryBranchs();
String cardNo = (String) request.getAttribute("cardNo");
%>

<%@include file="include.html"%>

<table class="table">
	<tr>
		<th>Branch Name</th>
		<th>Branch Address</th>
		<th>Select Branch</th>
	</tr>
	<%for(LibraryBranch lb: branches){ %>
	<tr>
		<td><%out.println(lb.getBranchName()); %></td>
		<td><%out.println(lb.getBranchAddress()); %></td>
		<td><button type="button" class="btn btn-md btn-success" onclick="javascript:location.href='selectABranch?branchId=<%=lb.getBranchId()%>&cardNo=<%=cardNo%>';">Select</button></td>
	</tr>
	<%} %>
</table>


