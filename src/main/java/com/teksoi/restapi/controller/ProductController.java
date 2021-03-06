package com.teksoi.restapi.controller;



import com.teksoi.restapi.dto.ProductsDto;
import com.teksoi.restapi.dto.Response;
import com.teksoi.restapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/pro")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/todo")
    @ResponseBody
    public Response create(@RequestBody ProductsDto toDoDto, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        Response response = productService.create(toDoDto);
        httpServletResponse.setStatus(response.getStatusCode());
        return response;
    }

    @GetMapping("/todo")
    @ResponseBody
    public Response getAll(HttpServletResponse httpServletResponse) {//To make it simple pagination has not been implemented

        Response response = productService.getAll();
        httpServletResponse.setStatus(response.getStatusCode());
        return response;
    }

    @PutMapping("/todo/{id}")
    @ResponseBody
    public Response update(@PathVariable("id") Long id, @RequestBody ProductsDto toDoDto, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        Response response = productService.update(id, toDoDto);
        httpServletResponse.setStatus(response.getStatusCode());
        return response;
    }

    @DeleteMapping("/todo/{id}")
    @ResponseBody
    public Response delete(@PathVariable("id") Long id, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        Response response = productService.delete(id);
        httpServletResponse.setStatus(response.getStatusCode());
        return response;
    }

    @DeleteMapping("/todo")
    @ResponseBody
    public Response delete(HttpServletResponse httpServletResponse) {
        Response response = productService.deleteAll();
        httpServletResponse.setStatus(response.getStatusCode());
        return response;
    }

}
