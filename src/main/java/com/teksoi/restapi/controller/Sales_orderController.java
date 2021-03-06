package com.teksoi.restapi.controller;



import com.teksoi.restapi.dto.Response;
import com.teksoi.restapi.dto.Sales_orderDto;
import com.teksoi.restapi.service.Sales_orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/sal")
public class Sales_orderController {

    private final Sales_orderService sales_orderService;

    @Autowired
    public Sales_orderController(Sales_orderService sales_orderService) {
        this.sales_orderService = sales_orderService;
    }


    @PostMapping("/todo")
    @ResponseBody
    public Response create(@RequestBody Sales_orderDto salesOrderDto, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        Response response = sales_orderService.create(salesOrderDto);
        httpServletResponse.setStatus(response.getStatusCode());
        return response;
    }

    @GetMapping("/todo")
    @ResponseBody
    public Response getAll(HttpServletResponse httpServletResponse) {//To make it simple pagination has not been implemented

        Response response = sales_orderService.getAll();
        httpServletResponse.setStatus(response.getStatusCode());
        return response;
    }

    @PutMapping("/todo/{id}")
    @ResponseBody
    public Response update(@PathVariable("id") Long id, @RequestBody Sales_orderDto sales_orderDto, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        Response response = sales_orderService.update(id, sales_orderDto);
        httpServletResponse.setStatus(response.getStatusCode());
        return response;
    }

    @DeleteMapping("/todo/{id}")
    @ResponseBody
    public Response delete(@PathVariable("id") Long id, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        Response response = sales_orderService.delete(id);
        httpServletResponse.setStatus(response.getStatusCode());
        return response;
    }

    @DeleteMapping("/todo")
    @ResponseBody
    public Response delete(HttpServletResponse httpServletResponse) {
        Response response = sales_orderService.deleteAll();
        httpServletResponse.setStatus(response.getStatusCode());
        return response;
    }

}
