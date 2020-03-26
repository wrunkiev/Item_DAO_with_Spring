package com.controller;

import com.model.Item;
import com.service.ItemService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /*public Item save(Item item)throws Exception{
        return itemService.save(item);
    }*/

    @RequestMapping(method = RequestMethod.GET, value = "/getItem", produces = "text/plain")
    public void findById(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            String idString = req.getParameter("id");
            long id = Long.parseLong(idString);
            Item item = itemService.findById(id);
            resp.getWriter().println(item.toString());
        }catch (NoSuchElementException e){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(resp.getStatus());
        }
        catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }
        //return itemService.findById(id);
    }

   /* public Item update(Item item)throws Exception {
        return itemService.update(item);
    }

    public void delete(long id)throws Exception{
        itemService.delete(id);
    }*/
}
