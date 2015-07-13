<%@include file="include.html"%>
<h2>Hello Borrower - Welcome to GCIT Library Management System</h2>
${result}
<h3>Do you want to:</h3>
<a data-toggle="modal" data-target="#myModal1" href="checkcardNo.jsp?action=checkout">Check out a book</a></td><br />
<a data-toggle="modal" data-target="#myModal1" href="checkcardNo.jsp?action=return">Return a book</a></td>


<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
