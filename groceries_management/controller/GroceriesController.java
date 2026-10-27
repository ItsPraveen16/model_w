package com.example.groceries_management.controller;

import com.example.groceries_management.model.Groceries;
import com.example.groceries_management.service.GroceriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groceries")
@CrossOrigin(origins = "http://localhost:5173")
public class GroceriesController {
    private GroceriesService gservice;
    public GroceriesController(GroceriesService gservice)
    {
        this.gservice=gservice;
    }
    @GetMapping
    public List<Groceries> GetAllGrocery()
    {
        return gservice.getAllGrocery();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Groceries> getGroceryById(
            @PathVariable String id) {

        Groceries grocery = gservice.getGroceryById(id);

        if (grocery == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(grocery);
    }
    @PostMapping
    public ResponseEntity<Groceries> addGrocery(@RequestBody Groceries grocery)
    {
        Groceries saved = gservice.addGrocery(grocery);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Groceries> updateGrocery(@PathVariable String id,@RequestBody Groceries grocery)
    {
        Groceries update = gservice.updateGrocery(id,grocery);
        if(update==null)
        {
            return ResponseEntity.notFound().build();
        }

            return ResponseEntity.ok(update);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrocery(@PathVariable String id)
    {
        boolean delete = gservice.deleteGrocery(id);
        if(!delete)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}