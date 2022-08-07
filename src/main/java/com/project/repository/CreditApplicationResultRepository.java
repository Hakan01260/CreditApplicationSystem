package com.project.repository;

import com.project.model.entity.CreditApplicationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditApplicationResultRepository extends JpaRepository<CreditApplicationResult,Long> {

    public List<CreditApplicationResult> findByCustomer_Id(Long identity);
}
