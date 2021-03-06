package com.teksoi.restapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Data
public class Sales_order extends BaseModel {

    private  double total_qty;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date delivery_date;

//    @ManyToOne
//    @JoinColumn(name = "id", referencedColumnName = "id")
//    private Products products;



    @ManyToOne
    @JoinColumn(name="PRODUCT_ID",referencedColumnName = "id")
    private Products products;





    public Sales_order() {
    }

    public Sales_order(double total_qty, Date delivery_date, Products products) {
        this.total_qty = total_qty;
        this.delivery_date = delivery_date;
        this.products = products;
    }
}
