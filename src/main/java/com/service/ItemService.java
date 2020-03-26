package com.service;

import com.DAO.ItemDAO;
import com.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


public class ItemService {

    @Autowired
    private ItemDAO itemDAO;

    public Item save(Item item)throws Exception{
        return itemDAO.save(item);
    }

    public Item findById(long id)throws NoSuchElementException {
        if(itemDAO.findById(id) == null){
            throw new NoSuchElementException();
        }
        return itemDAO.findById(id);
    }

    public Item update(Item item)throws Exception {
        return itemDAO.update(item);
    }

    public void delete(long id)throws Exception{
        itemDAO.delete(id);
    }
}
