package com.project.repository;

import com.project.model.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Long> {
}
