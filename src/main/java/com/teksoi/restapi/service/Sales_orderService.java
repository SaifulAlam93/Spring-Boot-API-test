package com.teksoi.restapi.service;

import com.teksoi.restapi.dto.Response;
import com.teksoi.restapi.dto.Sales_orderDto;

public interface Sales_orderService {
    Response create(Sales_orderDto sales_orderDto);

    Response getAll();

    Response update(Long id, Sales_orderDto sales_orderDto);

    Response delete(Long id);

    Response deleteAll();
}
