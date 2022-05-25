package com.library.orm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("Stocks")
public class Inventory {

	@Id
	private String bookID;
	private String stock;
	
	
	
	
	@Override
	public String toString() {
		return "Inventory [bookID=" + bookID + ", stock=" + stock + "]";
	}
	public Inventory() {
		super();
	}
	public Inventory(String bookID, String stock) {
		super();
		this.bookID = bookID;
		this.stock = stock;
	}
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	
	
}
