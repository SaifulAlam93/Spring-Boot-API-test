package com.teksoi.restapi.dto;

import lombok.Data;

@Data
public class ProductsDto {
    private Long id;
    private  String product_name;
    private  double opening_balance;
    private  String remarks;


}
