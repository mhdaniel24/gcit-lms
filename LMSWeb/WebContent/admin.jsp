<%@include file="include.html"%>
<h2>Hello Admin - Welcome to GCIT Library Management System</h2>
${result}
<h3>Authors</h3>
<a href="addAuthor.jsp">Add Author</a><br />
<a href="viewAuthors.jsp">View Authors</a><br />
<h3>Publishers</h3>
<a href="addPublisher.jsp">Add Publisher</a><br />
<a href="viewPublisher.jsp">View Publisher</a><br />
<h3>Borrowers</h3>
<a href="addBorrower.jsp">Add Borrower</a><br />
<a href="viewBorrowers.jsp">View Borrowers</a><br />
<h3>Library Branches</h3>
<a href="addBranch.jsp">Add Library Branch</a><br />
<a href="viewBranches.jsp">View Library Branches</a><br />
<h3>Book</h3>
<a href="viewBooks.jsp">View Books</a><br />
<a href="addBook.jsp">Add Book</a><br />
<h3>Genre</h3>
<a href="addGenre.jsp">Add Genre</a><br />
<h3>Book Loan</h3>
<a data-toggle="modal" data-target="#myModal1" href="dateTimePicker.jsp">Change Book Loan Due Date</a>


<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>

