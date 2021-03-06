package com.teksoi.restapi.dto;

import com.teksoi.restapi.model.Products;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Sales_orderDto {

    private Long id;
    private  double total_qty;
    private Date delivery_date;
    private Products products;


}
