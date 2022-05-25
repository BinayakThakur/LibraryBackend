package com.library.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.library.orm.BookData;
import com.library.orm.Inventory;
import com.library.repository.InventoryRepo;
import com.library.repository.PublicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
@RestController
public class ViewLayer{

	@Autowired
	PublicRepo repo;

	@Autowired
	InventoryRepo stock;


	@PostMapping("/importBooks")
	public String importBooks(@RequestBody Inventory invent) {
		try {
			if (stock.existsById(invent.getBookID())) {
				Inventory current = stock.findById(invent.getBookID()).get();
				if (Integer.parseInt(invent.getStock()) + Integer.parseInt(current.getStock()) > 30) {
					return "maximum limit reached";
				}
				invent.setStock(Integer.parseInt(invent.getStock()) + Integer.parseInt(current.getStock()) + "");
				stock.save(invent);
			} else {
				stock.save(invent);
			}
			return "success";
		} catch (Exception e) {
			return "error";
		}
	}

	@GetMapping("/getStocks")
	public List<Inventory> getStock() {
		return stock.findAll();
	}

	@GetMapping
	public Integer getStockById(@RequestBody String string) {
		try {
			String inv = stock.findById(string).get().getStock();
			return Integer.parseInt(inv);
		} catch (Exception e) {
			return -1;
		}

	}

	@GetMapping("/decStocks")
	public void decStock(String bookID) {
		System.out.println("decreased");
		Inventory changed = stock.findById(bookID).get();
		changed.setStock((Integer.parseInt(changed.getStock()) - 1) + "");
		stock.save(changed);
	}

	@GetMapping("/incStocks")
	public void incStock(String bookID) {
		Inventory changed = stock.findById(bookID).get();
		changed.setStock((Integer.parseInt(changed.getStock()) + 1) + "");
		stock.save(changed);

	}

	@PostMapping("/rentToMember")
	public String EncryptData(@RequestBody BookData transaction) {
		
		String bookID = "";
		Integer currentStock = 0;
		try {
			currentStock = getStockById(transaction.getBookID());
		} catch (Exception i) {
			return "error";
		}
		if (currentStock == 0) {
			return "minimum stock reached";
		}
		if (repo.existsById(transaction.getMemberID())) {
			bookID=transaction.getBookID();
			String booksRented = transaction.getBookID();
			BookData prev = repo.findById(transaction.getMemberID()).get();
			String[] books = prev.getBookID().split("%");
			System.out.println(Arrays.toString(books) + " " + transaction.getBookID());
			for (String i : books) {
				if (i.equals(transaction.getBookID())) {
					return "error";
				}
			}
			Integer price=100;
			transaction.setBookID(prev.getBookID() + transaction.getBookID() + "%");
			try {
				price=(Integer.parseInt(prev.getPrice()) + 100);
				transaction.setPrice(price+"");
			}
			catch(Exception nfe) {
				
			}

			if (price > 500) {
				return "debt";
			}

			repo.save(transaction);
			System.out.println(bookID);
			decStock(bookID);

			return "success";
		} else {
			bookID=transaction.getBookID();
			transaction.setBookID(transaction.getBookID() + "%");
			repo.save(transaction);
			decStock(bookID);
			return "success";
		}

	}

	@PostMapping("/returnFromMember")
	public String returnBook(@RequestBody BookData transaction) {
		if (repo.existsById(transaction.getMemberID())) {
			BookData prev = repo.findById(transaction.getMemberID()).get();
			String[] books = prev.getBookID().split("%");
			String newBookID = "";
			String bookID = transaction.getBookID();
			boolean deleted = false;
			for (String i : books) {
				if (i.equals(transaction.getBookID()) == false) {
					newBookID = newBookID + i + "%";
				} else {
					deleted = true;
				}
			}
			if (!deleted) {
				return "error";
			}
			if (newBookID.length() == 0) {
				repo.deleteById(transaction.getMemberID());
				incStock(bookID);
				return "success";
			}
			transaction.setBookID(newBookID);
			try {
				transaction.setPrice((Integer.parseInt(prev.getPrice()) - 100) + "");
			}
			catch(Exception nfe) {
				
			}
			
			repo.save(transaction);
			incStock(bookID);
			return "success";
		} else {
			return "error";
		}

	}
	@GetMapping("/getBooks")
	public List<BookData> getBooks() {
		return repo.findAll();
	}
	@RequestMapping("/Check")
	public @ResponseBody String smile() {

		return "Smiling";
	}

}
