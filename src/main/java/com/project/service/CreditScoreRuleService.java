package com.project.service;

import com.project.model.entity.Credit;
import com.project.model.entity.CreditScoreRule;
import com.project.model.entity.Customer;

public interface CreditScoreRuleService {

    CreditScoreRule findRule(Credit credit, Customer customer);
}
