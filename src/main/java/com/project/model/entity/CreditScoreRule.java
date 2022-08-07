package com.project.model.entity;

import com.project.model.enums.CreditScoreRuleResult;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CreditScoreRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int minScore;
    private int maxScore;
    private int minSalary;
    private int maxSalary;
    @Enumerated(EnumType.STRING)
    private CreditScoreRuleResult result;
    private Double creditLimit;

    public CreditScoreRule() {
    }

    public CreditScoreRule(int minScore, int maxScore, int minSalary, int maxSalary, CreditScoreRuleResult result) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.result = result;
    }

    public CreditScoreRule(int minScore, int maxScore, int minSalary, int maxSalary, CreditScoreRuleResult result, Double creditLimit) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.result = result;
        this.creditLimit = creditLimit;
    }

}
