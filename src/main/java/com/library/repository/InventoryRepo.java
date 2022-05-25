package com.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.library.orm.Inventory;

@Repository
public interface InventoryRepo extends MongoRepository<Inventory, String> {

}
