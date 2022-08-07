package com.project.service.implementations;

import com.google.common.collect.Sets;
import com.project.mapper.CustomerResponseMapper;
import com.project.model.api.request.CheckCreditLimitRequest;
import com.project.model.api.request.CustomerCreateRequest;
import com.project.model.api.request.CustomerUpdateRequest;
import com.project.model.api.response.CheckCreditLimitResponse;
import com.project.model.api.response.CustomerResponse;
import com.project.model.entity.Credit;
import com.project.model.entity.CreditApplicationResult;
import com.project.model.entity.CreditScoreRule;
import com.project.model.entity.Customer;
import com.project.model.enums.CreditScoreRuleResult;
import com.project.model.exception.IdentityNumberException;
import com.project.model.exception.NotFoundException;
import com.project.repository.CreditApplicationResultRepository;
import com.project.repository.CustomerRepository;
import com.project.service.CreditScoreRuleService;
import com.project.service.CreditService;
import com.project.service.CustomerService;
import com.project.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CreditService creditService;
    private final SmsService smsService;
    private final CreditScoreRuleService creditScoreRuleService;
    private final CustomerResponseMapper customerResponseMapper;
    private final CustomerRepository customerRepository;
    private final CreditApplicationResultRepository creditApplicationResultRepository;
    private final Set<Integer> digits = Sets.newHashSet(0, 2, 4, 6, 8);

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public CheckCreditLimitResponse checkCreditLimit(CheckCreditLimitRequest request) {
        Customer customer = customerRepository.findById(request.getIdentityNumber())
                .orElseThrow(() -> new NotFoundException("Customer not found by id: " + request.getIdentityNumber()));

        Credit credit = creditService.findCreditByIdentityNumber(request.getIdentityNumber());
        CreditScoreRule creditScoreRule = creditScoreRuleService.findRule(credit, customer);
        CreditScoreRuleResult result = creditScoreRule.getResult();

        CreditApplicationResult creditApplicationResult = new CreditApplicationResult();
        creditApplicationResult.setCreatedDate(new Date());
        creditApplicationResult.setCustomer(customer);
        if (result == CreditScoreRuleResult.REJECTED) {
            creditApplicationResult.setResult(false);
        } else {
            creditApplicationResult.setResult(true);
            creditApplicationResult.setCreditLimit(creditScoreRule.getCreditLimit());
        }
        CreditApplicationResult savedCreditApplicationResult = creditApplicationResultRepository.save(creditApplicationResult);
        smsService.sendSms(customer.getPhoneNumber(), getSmsResult(savedCreditApplicationResult));
        return new CheckCreditLimitResponse(savedCreditApplicationResult.getId(), savedCreditApplicationResult.isResult(), savedCreditApplicationResult.getCreditLimit());
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerResponseMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()) {
            throw new NotFoundException("Customer not found by id :" + id);
        }
        logger.info("Customer founded by id :" + id);
        return customerResponseMapper.map(optionalCustomer.get());
    }

    private int getMod(Long identityNumber) {
        return Long.valueOf(identityNumber % 10).intValue();
    }

    @Transactional
    public CustomerResponse create(CustomerCreateRequest request) {
        if (!digits.contains(getMod(request.getId()))) {
            throw new IdentityNumberException("The last digit of your ID number must be even !");
        }
        Customer customer = new Customer();
        customer.setId(request.getId());
        customer.setFistName(request.getFistName());
        customer.setLastName(request.getLastName());
        customer.setSalary(request.getSalary());
        customer.setPhoneNumber(request.getPhoneNumber());

        Customer createdCustomer = customerRepository.save(customer);
        logger.info("Customer saved...");
        return customerResponseMapper.map(createdCustomer);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()) {
            throw new NotFoundException("Customer not found by id: " + id);
        }
        Customer customer = optionalCustomer.get();
        customerRepository.deleteById(customer.getId());
        logger.info("Customer deleted by id: " + id);
    }

    @Transactional
    public CustomerResponse update(CustomerUpdateRequest request) {
        Optional<Customer> optionalCustomer = customerRepository.findById(request.getId());
        if (!optionalCustomer.isPresent()) {
            throw new NotFoundException("Customer not found by id :" + request.getId());
        }
        Customer customer = optionalCustomer.get();
        customer.setFistName(request.getFistName());
        customer.setLastName(request.getLastName());
        customer.setSalary(request.getSalary());
        customer.setPhoneNumber(request.getPhoneNumber());

        Customer createdCustomer = customerRepository.save(customer);
        logger.info("Customer updated...");
        return customerResponseMapper.map(createdCustomer);
    }

    @Transactional(readOnly = true)
    public int getNumberOfCustomerNumber() {
        return customerRepository.getNumberOfCustomerNumber();
    }

    private String getSmsResult(CreditApplicationResult result) {
        if (result.isResult()) {
            return "Krediniz onaylanmıştır. Kredi limiti: " + result.getCreditLimit();
        } else {
            return "Krediniz reddedilmiştir.";
        }
    }
}
