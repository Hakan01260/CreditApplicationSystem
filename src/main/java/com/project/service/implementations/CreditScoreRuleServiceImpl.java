package com.project.service.implementations;

import com.project.model.api.response.CheckCreditLimitResponse;
import com.project.model.entity.Credit;
import com.project.model.entity.CreditScoreRule;
import com.project.model.entity.Customer;
import com.project.model.enums.CreditScoreRuleResult;
import com.project.model.exception.NotFoundException;
import com.project.repository.CreditScoreRuleRepository;
import com.project.service.CreditScoreRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditScoreRuleServiceImpl implements CreditScoreRuleService {

    private final CreditScoreRuleRepository creditScoreRuleRepository;

    @Transactional(readOnly = true)
    public CreditScoreRule findRule(Credit credit, Customer customer){
        List<CreditScoreRule> creditScoreRules = creditScoreRuleRepository.findAll();
        for (CreditScoreRule creditScoreRule : creditScoreRules) {
            if (creditScoreRule.getMinScore() < credit.getScore() &&  creditScoreRule.getMaxScore() > credit.getScore()){
                if (creditScoreRule.getMinSalary() < customer.getSalary() && creditScoreRule.getMaxSalary() > customer.getSalary()) {
                  if (creditScoreRule.getResult() == CreditScoreRuleResult.MULTIPLIER){
                      creditScoreRule.setCreditLimit(customer.getSalary() * 4);
                  }
                    return creditScoreRule;
                }
            }
        }
        throw new NotFoundException("CreditScoreRule not found");
    }
}
