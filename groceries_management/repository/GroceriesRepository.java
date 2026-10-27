package com.example.groceries_management.repository;

import com.example.groceries_management.model.Groceries;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroceriesRepository extends MongoRepository<Groceries,String> {
}
