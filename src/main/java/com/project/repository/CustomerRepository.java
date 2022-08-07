package com.project.repository;

import com.project.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    // JPQL
    @Query("select count(c) from Customer c")
    int getNumberOfCustomerNumber();

}
