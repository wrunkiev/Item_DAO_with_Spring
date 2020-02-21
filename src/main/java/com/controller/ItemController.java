package com.controller;

import com.model.Item;
import com.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    public Item save(Item item)throws Exception{
        checkItemNull(item);
        return itemService.save(item);
    }

    public Item findById(long id)throws Exception{
        return itemService.findById(id);
    }

    public Item update(Item item)throws Exception {
        checkItemNull(item);
        if(item.getId() == null){
            throw new Exception("Exception in method ItemController.update. Enter id for item.");
        }
        return itemService.update(item);
    }

    public void delete(long id)throws Exception{
        itemService.delete(id);
    }

    private static void checkItemNull(Item item)throws Exception{
        if(item == null){
            throw new Exception("Exception in method ItemController.checkItemNull. Item can't be null.");
        }
    }
}
