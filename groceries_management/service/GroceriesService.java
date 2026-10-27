package com.example.groceries_management.service;

import com.example.groceries_management.model.Groceries;
import com.example.groceries_management.repository.GroceriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceriesService {
    private GroceriesRepository grepo;
    public GroceriesService(GroceriesRepository grepo)
    {
        this.grepo=grepo;
    }

    public Groceries addGrocery(Groceries grocery)
    {
        return grepo.save(grocery);
    }

    public List<Groceries> getAllGrocery()
    {
        return grepo.findAll();
    }

    public Groceries getGroceryById(String id)
    {
        return grepo.findById(id).orElse(null);
    }
    public Groceries updateGrocery(String id,Groceries newgrocery)
    {
        Groceries existing = grepo.findById(id).orElse(null);
        if(existing==null)
            return null;
        existing.setName(newgrocery.getName());
        existing.setCategory(newgrocery.getCategory());
        existing.setPrice(newgrocery.getPrice());
        existing.setQuantity(newgrocery.getQuantity());
        return grepo.save(existing);
    }
    public boolean deleteGrocery(String id)
    {
        if(grepo.findById(id).orElse(null)==null)
            return false;
        grepo.deleteById(id);
        return true;
    }

}
