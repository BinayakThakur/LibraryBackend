package com.library.orm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("memberBookData")
public class BookData {

	@Id
	private String memberID;
	private String bookID;
	private String price;
	
	public BookData() {
		super();
	}
	public BookData(String memberID, String bookID, String price) {
		super();
		this.memberID = memberID;
		this.bookID = bookID;
		this.price = price;
	}
	public BookData(String memberID, String bookID) {
		super();
		this.memberID = memberID;
		this.bookID = bookID;
	}
	@Override
	public String toString() {
		return "BookData [memberID=" + memberID + ", bookID=" + bookID + "]";
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
}
