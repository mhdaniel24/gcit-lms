
<%String action = request.getParameter("action");%>


<div class="modal-body">
<form action="checkcardNo" method="post">
		Enter your card number: <input type="text" name="cardNo"/>
		<input type="submit"/>
		<input type="hidden" name="action" value=<%=action%>>
</form>
</div>

