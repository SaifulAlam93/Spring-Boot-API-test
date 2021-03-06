package com.teksoi.restapi.repository;

import com.teksoi.restapi.model.Sales_order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sales_order_repository extends  JpaRepository<Sales_order, Long> {
    @Modifying
    @Query("update Sales_order s set s.active = ?1")
    int deleteAll(Boolean active);

    List<Sales_order> findAllByActiveTrue();
}
