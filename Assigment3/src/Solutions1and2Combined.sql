Query 1:

Version 1:

SELECT tbl_book_copies.noOfCopies 
FROM tbl_book, tbl_book_copies, tbl_library_branch 
WHERE tbl_book.title = 'book1' 
AND tbl_book.bookId = tbl_book_copies.bookId 
AND tbl_book_copies.branchId =  tbl_library_branch.branchId 
AND tbl_library_branch.branchName = 'branch1';

Version 2:
SELECT noOfCopies
FROM ((tbl_book NATURAL JOIN tbl_book_copies ) NATURAL JOIN tbl_library_branch) 
WHERE title='book1' AND BranchName='branch1';

Query 2:

Version 1:
SELECT tbl_library_branch.branchName, tbl_book_copies.noOfCopies 
FROM tbl_book, tbl_book_copies, tbl_library_branch 
WHERE tbl_book.title = 'book1' 
AND tbl_book.bookId = tbl_book_copies.bookId 
AND tbl_book_copies.branchId =  tbl_library_branch.branchId;

Version 2:
SELECT BranchName, noOfCopies
FROM ((tbl_book NATURAL JOIN tbl_book_copies) NATURAL JOIN tbl_library_branch ) 
WHERE title='book1';

QUERY 3:
SELECT name FROM tbl_borrower WHERE cardNo NOT IN (SELECT cardNo FROM tbl_book_loans);

Query 4:

SELECT tbl_book.title, tbl_borrower.name, tbl_borrower.address
 FROM tbl_book, tbl_book_loans, tbl_borrower, tbl_library_branch 
 WHERE tbl_book.bookId = tbl_book_loans.bookId 
 AND tbl_book_loans.branchId = tbl_library_branch.branchId 
 AND tbl_library_branch.branchName = 'branch1'
 AND tbl_book_loans.dueDate = CURDATE() 
 AND tbl_borrower.cardNo = tbl_book_loans.cardNo;

Query 5:
Use library;
SELECT tbl_library_branch.branchName, COUNT(tbl_book_loans.branchId) 
FROM tbl_library_branch, tbl_book_loans 
WHERE tbl_library_branch.branchId = tbl_book_loans.branchId 
group by tbl_library_branch.branchId;

QUERY 6:
SELECT tbl_borrower.name, tbl_borrower.address, COUNT(tbl_book_loans.cardNo)
FROM tbl_borrower, tbl_book_loans 
WHERE tbl_book_loans.cardNo = tbl_borrower.cardNo
GROUP BY tbl_borrower.cardNo 
Having COUNT(tbl_book_loans.cardNo) > 5;

QUERY 7:
SELECT tbl_book.title, tbl_book_copies.noOfCopies 
FROM tbl_book, tbl_book_authors, tbl_author, tbl_book_copies, tbl_library_branch
WHERE tbl_book_authors.bookId = tbl_book.bookId 
AND tbl_book_authors.authorId = tbl_author.authorId
AND tbl_book_copies.bookId = tbl_book.bookId
AND tbl_library_branch.branchId = tbl_book_copies.branchId
AND tbl_library_branch.branchName = 'branch1'
AND tbl_author.authorName = 'author1';


----------------------------------------------------------------------------------


Query 8:
Use Company;
SELECT fname,lname 
FROM employee, project, works_on
WHERE employee.ssn=works_on.essn
AND works_on.pno=project.pnumber
AND pname='ProductX'
AND dno=5
AND hours >10;

Query 9:
SELECT project.pname,SUM(hours) FROM works_on, project WHERE works_on.pno = Project.pnumber GROUP BY project.pname;

QUERY 10:
SELECT employee.fname, employee.lname FROM employee
WHERE NOT EXISTS (SELECT project.pnumber FROM project WHERE NOT EXISTS 
(SELECT works_on.pno FROM works_on WHERE works_on.pno=project.pnumber AND employee.ssn=works_on.essn));

QUERY 11:
use Company;
SELECT fname,lname
FROM employee as e
WHERE NOT EXISTS(SELECT w.pno FROM works_on as w WHERE e.ssn=w.essn);

QUERY 12:(uses minus)
Rodolfo's answer:
SELECT e.fname, e.address FROM employee as e, project as p, works_on as wn, dept_locations as dl WHERE
 p.plocation = "Houston" AND wn.essn = e.ssn AND p.pnumber = wn.pno AND dl.dlocation != "Houston" AND e.dno = dl.dnumber GROUP BY e.fname;

QUERY 13:
use Company;
SELECT lname FROM employee, department
WHERE employee.ssn=department.mgrssn
AND NOT EXISTS (SELECT * FROM dependent WHERE department.mgrssn=dependent.essn);

QUERY 14:
SELECT fname, lname, salary FROM employee WHERE employee.salary > (SELECT AVG(employee.salary) FROM employee) ORDER BY salary DESC;

QUETRY 15:
SELECT e1.fname, e1.lname, e1.salary FROM employee as e1 WHERE e1.salary  > (SELECT AVG(salary) FROM employee as e2 WHERE e1.dno = e2.dno);
