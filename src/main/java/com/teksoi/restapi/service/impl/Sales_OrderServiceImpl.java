package com.teksoi.restapi.service.impl;


import com.teksoi.restapi.dto.Response;
import com.teksoi.restapi.dto.Sales_orderDto;
import com.teksoi.restapi.exception.ResourceNotFoundException;
import com.teksoi.restapi.model.Sales_order;
import com.teksoi.restapi.repository.Sales_order_repository;
import com.teksoi.restapi.service.Sales_orderService;
import com.teksoi.restapi.utils.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("sales_orderService")
public class Sales_OrderServiceImpl implements Sales_orderService {

    private final String root = "Sales_Order";

    private final Sales_order_repository sales_order_repository;
    private final ModelMapper modelMapper;

    @Autowired
    public Sales_OrderServiceImpl(Sales_order_repository sales_order_repository, ModelMapper modelMapper) {
        this.sales_order_repository = sales_order_repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response create(Sales_orderDto sales_orderDto) {
        Sales_order sales_order = modelMapper.map(sales_orderDto, Sales_order.class);
        sales_order.setActive(true);
        sales_order = sales_order_repository.save(sales_order);
        if (sales_order != null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, null, String.format("Created successfully", root));
        }
        return ResponseBuilder.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occurred");
    }

    @Override
    public Response getAll() {
        List<Sales_order> toDoList = sales_order_repository.findAllByActiveTrue();
        List<Sales_orderDto> responseDtos = new ArrayList<>();
        toDoList.forEach(course -> {
            Sales_orderDto sales_orderDto = modelMapper.map(course, Sales_orderDto.class);
            responseDtos.add(sales_orderDto);
        });

        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, responseDtos, responseDtos.size(), String.format("%s list", root));
    }

    @Override
    public Response update(Long id, Sales_orderDto sales_orderDto) {
        Optional<Sales_order> optionalToDo = sales_order_repository.findById(id);
        if (!optionalToDo.isPresent()) {
            return ResponseBuilder.getFailResponse(HttpStatus.NOT_FOUND, String.format("Requested %s could not be found", root));
        }

        try {
            Sales_order sales_order = optionalToDo.get();
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.map(sales_orderDto, sales_order);
            sales_order = sales_order_repository.save(sales_order);

            if (sales_order != null) {
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
        Optional<Sales_order> optionalCourse = sales_order_repository.findById(id);

        if (!optionalCourse.isPresent()) {
            return ResponseBuilder.getFailResponse(HttpStatus.NOT_FOUND, String.format("Requested %s could not be found", root));
        }

        try {
            Sales_order sales_order = optionalCourse.get();
            sales_order.setActive(false); //Soft delete by setting active to false
            sales_order = sales_order_repository.save(sales_order);

            if (sales_order != null) {
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
        sales_order_repository.deleteAll(false);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, null, String.format("%s deleted successfully", root));
    }
}
