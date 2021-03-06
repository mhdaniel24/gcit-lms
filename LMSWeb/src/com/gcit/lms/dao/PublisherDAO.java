package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.*;

public class PublisherDAO extends BaseDAO{
	public PublisherDAO(Connection conn) throws Exception {
		super(conn);
	}

	public void create(Publisher publisher) throws Exception {
		int pubId = saveWithID("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values(?, ?, ?)",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone() });
		//TODO: add the books that belong to it
		for(Book b: publisher.getBooks()){
			save("update tbl_book set pubId = ? where bookId = ?", 
				new Object[]{pubId, b.getBookId()});
		}
	}

	public void update(Publisher publisher) throws Exception {
		save("update tbl_publisher set publisherName = ?, publisherAddress=?, publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),  publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId() });

	}

	//do I have to set the book's pubId field = NULL or would that also happen with the cascade db
	public void delete(Publisher publisher) throws Exception {
		save("delete from tbl_publisher where publisherId = ?",
				new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> readAll() throws Exception{
		return (List<Publisher>) read("select * from tbl_publisher", null);
		
	}

	public Publisher readOne(int publisherId) throws Exception {
		List<Publisher> publishers = (List<Publisher>) read("select * from tbl_publisher where publisherId = ?", new Object[] {publisherId});
		if(publishers!=null && publishers.size()>0){
			return publishers.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<Publisher> publishers =  new ArrayList<Publisher>();
		
		while(rs.next()){
			BookDAO bDao = new BookDAO(getConnection());
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			
			@SuppressWarnings("unchecked")
			List<Book> books = (List<Book>) bDao.readFirstLevel("select * from tbl_book where bookId = ?"
					, new Object[] {rs.getInt("publisherId")});
			
			p.setBooks(books);
			publishers.add(p);
		}
		return publishers;
	}

	@Override
	public List extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Publisher> publishers =  new ArrayList<Publisher>();
		//BookDAO bDao = new BookDAO(getConnection());
		
		while(rs.next()){
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			
			publishers.add(p);
		}
		return publishers;
	}
}
