package com.teksoi.restapi.model;


import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Products extends BaseModel {


    private  String product_name;
    private  double opening_balance;
    private  String remarks;

}
