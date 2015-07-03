package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.Author;
import domain.BookLoans;

public class LoanDAO extends BaseDAO{
	
	public void create(BookLoans bookLoan) throws Exception {
		save("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values(?,?,?,?,?)",
				new Object[] { author.getAuthorName() });
	}

	public void update(Author author) throws Exception {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });

	}

	public void delete(Author author) throws Exception {
		save("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorId() });
	}

	public List<Author> readAll() throws Exception{
		return (List<Author>) read("select * from tbl_author", null);
		
	}

	public Author readOne(int authorId) throws Exception {
		List<Author> authors = (List<Author>) read("select * from tbl_author where authorId = ?", new Object[] {authorId});
		if(authors!=null && authors.size()>0){
			return authors.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<Author> authors =  new ArrayList<Author>();
		
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			
			authors.add(a);
		}
		return authors;
	}
}
