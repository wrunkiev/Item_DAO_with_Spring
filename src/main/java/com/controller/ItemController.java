package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;

@Controller
public class ItemController {
    private BufferedReader bufferedReader;

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST, value = "/saveItem", produces = "text/plain")
    public void save(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            Item item = requestRead(req);
            item.setId(null);
            itemService.save(item);
        }catch (IllegalArgumentException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(resp.getStatus());
        }catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }finally {
            bufferedReader.close();
        }
    }

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
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/putItem", produces = "text/plain")
    public void update(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        try{
            Item item = requestRead(req);
            itemService.update(item);
        }catch (IllegalArgumentException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(resp.getStatus());
        }catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }finally {
            bufferedReader.close();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteItem", produces = "text/plain")
    public void delete(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        try{
            Item item = requestRead(req);
            itemService.delete(item.getId());
        }catch (IllegalArgumentException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(resp.getStatus());
        }catch (HibernateException e){
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(resp.getStatus());
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(resp.getStatus());
        }finally {
            bufferedReader.close();
        }
    }

    private Item getItem(String response) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            Item item = mapper.readValue(response, Item.class);
            return item;
        }catch (Exception e){
            return null;
        }
    }

    private String bodyContent(BufferedReader reader) throws IOException {
        String input;
        StringBuilder requestBody = new StringBuilder();
        while((input = reader.readLine()) != null) {
            requestBody.append(input);
        }
        return requestBody.toString();
    }

    private Item requestRead(HttpServletRequest req)throws IllegalArgumentException, IOException{
        bufferedReader = req.getReader();
        String str = bodyContent(bufferedReader);
        Item item = getItem(str);
        if(item == null){
            throw new IllegalArgumentException("Request is empty");
        }
        return item;
    }
}