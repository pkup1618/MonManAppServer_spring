package com.example.MonManAppServer_spring.repositories;

import com.example.MonManAppServer_spring.models.EarningDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface EarningRepository extends JpaRepository<EarningDB, Long> {

    @Query("SELECT e FROM EarningDB e WHERE e.day = :day")
    List<EarningDB> findAllByDate(@Param("day") Date day);
}
