package com.example.MonManAppServer_spring.repositories;

import com.example.MonManAppServer_spring.models.DateDB;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface DateRepository extends JpaRepository<DateDB, java.sql.Date> {
}
