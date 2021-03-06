package com.teksoi.restapi.service;


import com.teksoi.restapi.dto.ProductsDto;
import com.teksoi.restapi.dto.Response;
import org.springframework.data.domain.Page;

public interface ProductService {
    Response create(ProductsDto toDoDto);

    Response getAll();

    Response update(Long id, ProductsDto toDoDto);

    Response delete(Long id);

    Response deleteAll();


}
