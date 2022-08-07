package com.project.service;

import com.project.model.entity.Credit;
import com.project.model.entity.CreditScoreRule;
import com.project.model.entity.Customer;
import com.project.model.enums.CreditScoreRuleResult;
import com.project.model.exception.NotFoundException;
import com.project.repository.CreditScoreRuleRepository;
import com.project.service.implementations.CreditScoreRuleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditScoreRuleServiceImplTest {

    private CreditScoreRuleServiceImpl creditScoreRuleService;
    private CreditScoreRuleRepository creditScoreRuleRepository;
    private final List<CreditScoreRule> creditScoreRules = Arrays.asList(
            new CreditScoreRule(0, 500, 0, 5000, CreditScoreRuleResult.REJECTED),
            new CreditScoreRule(500, 1000, 0, 5000, CreditScoreRuleResult.APPROVED, 10000.0),
            new CreditScoreRule(500, 1000, 5000, Integer.MAX_VALUE, CreditScoreRuleResult.APPROVED, 20000.0),
            new CreditScoreRule(1000, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, CreditScoreRuleResult.MULTIPLIER)
    );


    @BeforeEach
    void setUp() {
        creditScoreRuleRepository = Mockito.mock(CreditScoreRuleRepository.class);
        creditScoreRuleService = new CreditScoreRuleServiceImpl(creditScoreRuleRepository);
    }

    @Test
    void it_should_return_rejected_credit_score_rule() {
        // Given
        Customer customer = new Customer();
        customer.setSalary(1000);

        Credit credit = new Credit();
        credit.setScore(300);


        Mockito.when(creditScoreRuleRepository.findAll()).thenReturn(creditScoreRules);

        // When
        CreditScoreRule creditScoreRule = creditScoreRuleService.findRule(credit, customer);


        // Then
        Assertions.assertEquals(CreditScoreRuleResult.REJECTED, creditScoreRule.getResult());
        Assertions.assertEquals(null,creditScoreRule.getCreditLimit());
    }

    @Test
    void it_should_return_approved_credit_score_rule() {
        // Given
        Customer customer = new Customer();
        customer.setSalary(3000);

        Credit credit = new Credit();
        credit.setScore(700);


        Mockito.when(creditScoreRuleRepository.findAll()).thenReturn(creditScoreRules);

        // When
        CreditScoreRule creditScoreRule = creditScoreRuleService.findRule(credit, customer);


        // Then
        Assertions.assertEquals(CreditScoreRuleResult.APPROVED, creditScoreRule.getResult());
        Assertions.assertEquals(10000.0,creditScoreRule.getCreditLimit());
    }

    @Test
    void it_should_return_approved_with_high_salary_credit_score_rule() {
        // Given
        Customer customer = new Customer();
        customer.setSalary(7000);

        Credit credit = new Credit();
        credit.setScore(800);


        Mockito.when(creditScoreRuleRepository.findAll()).thenReturn(creditScoreRules);

        // When
        CreditScoreRule creditScoreRule = creditScoreRuleService.findRule(credit, customer);


        // Then
        Assertions.assertEquals(CreditScoreRuleResult.APPROVED, creditScoreRule.getResult());
        Assertions.assertEquals(20000.0,creditScoreRule.getCreditLimit());
    }

    @Test
    void it_should_return_multiplier_credit_score_rule() {
        // Given
        Customer customer = new Customer();
        customer.setSalary(8000);

        Credit credit = new Credit();
        credit.setScore(1200);

        Mockito.when(creditScoreRuleRepository.findAll()).thenReturn(creditScoreRules);

        // When
        CreditScoreRule creditScoreRule = creditScoreRuleService.findRule(credit, customer);


        // Then
        Assertions.assertEquals(CreditScoreRuleResult.MULTIPLIER, creditScoreRule.getResult());
        Assertions.assertEquals(customer.getSalary()*4,creditScoreRule.getCreditLimit());
    }

    @Test
    void it_should_throw_not_found_exception_when_find_credit_score_rule() {

        Customer customer = new Customer();
        customer.setSalary(8000);

        Credit credit = new Credit();
        credit.setScore(-20);

        Mockito.when(creditScoreRuleRepository.findAll()).thenReturn(creditScoreRules);

        // When
        NotFoundException exception = catchThrowableOfType(() -> creditScoreRuleService.findRule(credit,customer), NotFoundException.class);

        // Then
        assertEquals("CreditScoreRule not found", exception.getMessage());
    }
}