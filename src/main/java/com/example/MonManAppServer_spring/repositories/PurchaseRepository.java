package com.example.MonManAppServer_spring.repositories;

import com.example.MonManAppServer_spring.models.EarningDB;
import com.example.MonManAppServer_spring.models.PurchaseDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseDB, Long> {

    @Query("SELECT p FROM PurchaseDB p WHERE p.day = :day")
    List<PurchaseDB> findAllByDate(@Param("day") Date day);
}