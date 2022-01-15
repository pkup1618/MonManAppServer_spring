package com.example.MonManAppServer_spring.repositories;

import com.example.MonManAppServer_spring.models.DateDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Learn more about @Repository annotation
@Repository
public interface DateRepository extends JpaRepository<DateDB, java.sql.Date> {

}
