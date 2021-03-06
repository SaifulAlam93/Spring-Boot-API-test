package com.teksoi.restapi.service.impl;


import com.teksoi.restapi.dto.ProductsDto;
import com.teksoi.restapi.dto.Response;
import com.teksoi.restapi.exception.ResourceNotFoundException;
import com.teksoi.restapi.model.Products;
import com.teksoi.restapi.repository.ProductRepository;
import com.teksoi.restapi.service.ProductService;
import com.teksoi.restapi.utils.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("productService")
public class ProductsServiceImpl implements ProductService {


    private final String root = "ToDo";

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductsServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response create(ProductsDto productsDto) {
        Products products = modelMapper.map(productsDto, Products.class);
        products.setActive(true);
        products = productRepository.save(products);
        if (products != null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, null, String.format("Created successfully", root));
        }
        return ResponseBuilder.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occurred");
    }

    @Override
    public Response getAll() {
        List<Products> toDoList = productRepository.findAllByActiveTrue();
        List<ProductsDto> responseDtos = new ArrayList<>();
        toDoList.forEach(course -> {
            ProductsDto toDoDto = modelMapper.map(course, ProductsDto.class);
            responseDtos.add(toDoDto);
        });

        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, responseDtos, responseDtos.size(), String.format("%s list", root));
    }

    @Override
    public Response update(Long id, ProductsDto productsDto) {
        Optional<Products> optionalToDo = productRepository.findById(id);
        if (!optionalToDo.isPresent()) {
            return ResponseBuilder.getFailResponse(HttpStatus.NOT_FOUND, String.format("Requested %s could not be found", root));
        }

        try {
            Products products = optionalToDo.get();
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.map(productsDto, products);
            products = productRepository.save(products);

            if (products != null) {
                return ResponseBuilder.getSuccessResponse(HttpStatus.OK, null, String.format("%s updated successfully", root));
            }
            return ResponseBuilder.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occurred");

        } catch (NullPointerException e) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Response delete(Long id) {
        Optional<Products> optionalCourse = productRepository.findById(id);

        if (!optionalCourse.isPresent()) {
            return ResponseBuilder.getFailResponse(HttpStatus.NOT_FOUND, String.format("Requested %s could not be found", root));
        }

        try {
            Products toDo = optionalCourse.get();
            toDo.setActive(false); //Soft delete by setting active to false
            toDo = productRepository.save(toDo);

            if (toDo != null) {
                return ResponseBuilder.getSuccessResponse(HttpStatus.OK, null, String.format("%s deleted successfully", root));
            }
            return ResponseBuilder.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occurred");
        } catch (ResourceNotFoundException e) {
            return ResponseBuilder.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NullPointerException e) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Response deleteAll() {
        productRepository.deleteAll(false);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, null, String.format("%s deleted successfully", root));
    }
}
