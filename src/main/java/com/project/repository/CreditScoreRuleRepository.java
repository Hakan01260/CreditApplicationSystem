package com.project.repository;

import com.project.model.entity.CreditScoreRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditScoreRuleRepository extends JpaRepository<CreditScoreRule,Long> {
}
