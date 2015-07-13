<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%AdministrativeService adminService = new AdministrativeService();
 String bookId = request.getParameter("bookId");
 String branchId = request.getParameter("branchId");
 Book book = adminService.readOneBook(Integer.parseInt(bookId));
%>
<div class="modal-body">
<form action="addCopies" method="post">
			Enter new number of copies: <input type="text" name="numbCopies">
			<input type="hidden" name="bookId" value=<%=bookId %>>
			<input type="hidden" name="branchId" value=<%=branchId%>>
		<input type="submit"/>
</form>
</div>

