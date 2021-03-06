package com.teksoi.restapi.repository;

import com.teksoi.restapi.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    @Modifying
    @Query("update Products p set p.active = ?1")
    int deleteAll(Boolean active);

    List<Products> findAllByActiveTrue();
}
