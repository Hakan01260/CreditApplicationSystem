package com.project;

import com.project.model.entity.Credit;
import com.project.model.entity.CreditScoreRule;
import com.project.model.entity.Customer;
import com.project.model.enums.CreditScoreRuleResult;
import com.project.repository.CreditRepository;
import com.project.repository.CreditScoreRuleRepository;
import com.project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CreditApplicationSystemApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CreditApplicationSystemApplication.class, args);
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CreditScoreRuleRepository creditScoreRuleRepository;

    @Autowired
    private CreditRepository creditRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Customer customer = new Customer(1234568l,"hakan","erdogan",4500,"05555550");
        customerRepository.save(customer);

        CreditScoreRule creditScoreRule1 = new CreditScoreRule(0, 500, 0, 5000, CreditScoreRuleResult.REJECTED);
        CreditScoreRule creditScoreRule2 = new CreditScoreRule(500, 1000, 0, 5000, CreditScoreRuleResult.APPROVED, 10000.0);
        CreditScoreRule creditScoreRule3 = new CreditScoreRule(500, 1000, 5000, Integer.MAX_VALUE, CreditScoreRuleResult.APPROVED, 20000.0);
        CreditScoreRule creditScoreRule4 = new CreditScoreRule(1000,  Integer.MAX_VALUE, 0, Integer.MAX_VALUE, CreditScoreRuleResult.MULTIPLIER);

        List<CreditScoreRule> creditScoreRules = Arrays.asList(creditScoreRule1,creditScoreRule2,creditScoreRule3,creditScoreRule4);

        creditScoreRuleRepository.saveAll(creditScoreRules);

        Credit credit1 = new Credit(2,550);
        Credit credit2 = new Credit(4,1000);
        Credit credit3 = new Credit(6,400);
        Credit credit4 = new Credit(8,900);
        Credit credit5 = new Credit(0,2000);

        List<Credit> credits = Arrays.asList(credit1,credit2,credit3,credit4,credit5);

        creditRepository.saveAll(credits);
    }
}
