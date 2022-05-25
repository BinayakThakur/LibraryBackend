package com.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.library.orm.BookData;

@Repository
public interface PublicRepo extends  MongoRepository<BookData, String> {

}
