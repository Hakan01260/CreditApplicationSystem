package com.project.service;

import com.project.mapper.CustomerResponseMapper;
import com.project.model.api.request.CheckCreditLimitRequest;
import com.project.model.api.request.CustomerCreateRequest;
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
import com.project.service.implementations.CreditScoreRuleServiceImpl;
import com.project.service.implementations.CreditServiceImpl;
import com.project.service.implementations.CustomerServiceImpl;
import com.project.service.implementations.SmsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerServiceImpl customerService;

    private CustomerRepository customerRepository;
    private CreditService creditService;
    private SmsService smsService;
    private CreditScoreRuleService creditScoreRuleService;
    private CustomerResponseMapper customerResponseMapper;
    private CreditApplicationResultRepository creditApplicationResultRepository;

    @BeforeEach
    void setUp() {

        customerRepository = Mockito.mock(CustomerRepository.class);
        creditService = Mockito.mock(CreditServiceImpl.class);
        smsService = Mockito.mock(SmsServiceImpl.class);
        creditScoreRuleService = Mockito.mock(CreditScoreRuleServiceImpl.class);
        customerResponseMapper = Mockito.mock(CustomerResponseMapper.class);
        creditApplicationResultRepository = Mockito.mock(CreditApplicationResultRepository.class);
        customerService = new CustomerServiceImpl(creditService, smsService, creditScoreRuleService, customerResponseMapper, customerRepository, creditApplicationResultRepository);
    }

    @Test
    public void it_should_save_and_return_customer_when_called_create_customer_request(){
        // Given
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setId(45678L);
        request.setFistName("Baki");
        request.setLastName("Aykan");
        request.setSalary(5600);
        request.setPhoneNumber("5354564");

        Customer createdCustomer = new Customer();
        createdCustomer.setId(request.getId());
        createdCustomer.setFistName(request.getFistName());
        createdCustomer.setLastName(request.getLastName());
        createdCustomer.setSalary(request.getSalary());
        createdCustomer.setPhoneNumber(request.getPhoneNumber());

        CustomerResponse customerResponse = new CustomerResponse(createdCustomer.getId(),createdCustomer.getFistName(),
                createdCustomer.getLastName(),createdCustomer.getSalary(),createdCustomer.getPhoneNumber());

        when(customerRepository.save(Mockito.any())).thenReturn(createdCustomer);
        when(customerResponseMapper.map(createdCustomer)).thenReturn(customerResponse);

        // When

        CustomerResponse response = customerService.create(request);

        // Then
        Mockito.verify(customerRepository).save(createdCustomer);
        Mockito.verify(customerResponseMapper).map(createdCustomer);
        assertEquals(request.getId(), response.getId());
    }

    @Test
    public void it_should_throw_identity_number_exception_when_called_create_customer_request() {
        // Given
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setId(45671L);

        // When
        IdentityNumberException exception =
                 catchThrowableOfType(() -> customerService.create(request), IdentityNumberException.class);

        // Then
        assertEquals("The last digit of your ID number must be even !", exception.getMessage());
    }

    @Test
    public void it_should_throw_not_found_exception_when_customer_not_found() {
        // Given

        Long id = 12312L;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // When
        NotFoundException exception = catchThrowableOfType(() -> customerService.deleteById(id), NotFoundException.class);

        // Then
        assertEquals("Customer not found by id: " + id, exception.getMessage());
        verify(customerRepository, times(0)).deleteById(Mockito.any());
    }

    @Test
    public void it_should_find_customer_when_customer_is_exists(){
        // Given
        Long id = 12312L;

        Customer customer = new Customer();
        customer.setId(id);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // When
        customerService.findById(id);

        // Then
        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    public void it_should_delete_customer_when_customer_is_exists(){
        // Given
        Long id = 12312L;

        Customer customer = new Customer();
        customer.setId(id);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // When
        customerService.deleteById(id);

        // Then
        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test
    public void it_should_throw_not_found_exception_when_checking_customer_credit_limit() {
        // Given

        long id = 12312L;

        CheckCreditLimitRequest request = new CheckCreditLimitRequest();
        request.setIdentityNumber(id);

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // When
        NotFoundException exception = catchThrowableOfType(() -> customerService.checkCreditLimit(request), NotFoundException.class);

        // Then
        assertEquals("Customer not found by id: " + id, exception.getMessage());
        verifyNoInteractions(creditService);
        verifyNoInteractions(creditScoreRuleService);
    }


    @Test
    public void it_should_reject_credit_application_when_customer_credit_check_limit() {
        // Given
        Long id = 12312L;

        Customer customer = new Customer();
        customer.setId(id);
        customer.setSalary(1000);

        CheckCreditLimitRequest request = new CheckCreditLimitRequest();
        request.setIdentityNumber(id);

        Credit credit = new Credit();

        CreditScoreRule creditScoreRule = new CreditScoreRule();
        creditScoreRule.setResult(CreditScoreRuleResult.REJECTED);

        CreditApplicationResult savedCreditApplicationResult = new CreditApplicationResult();

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(creditService.findCreditByIdentityNumber(request.getIdentityNumber())).thenReturn(credit);
        when(creditScoreRuleService.findRule(credit, customer)).thenReturn(creditScoreRule);
        when(creditApplicationResultRepository.save(Mockito.any())).thenReturn(savedCreditApplicationResult);


        // When
        CheckCreditLimitResponse checkCreditLimitResponse = customerService.checkCreditLimit(request);

        // Then
        ArgumentCaptor<CreditApplicationResult> argumentCaptor = ArgumentCaptor.forClass(CreditApplicationResult.class);
        verify(creditApplicationResultRepository).save(argumentCaptor.capture());
        CreditApplicationResult argumentCaptorValue = argumentCaptor.getValue();
        assertFalse(argumentCaptorValue.isResult());
        assertNotNull(argumentCaptorValue.getCustomer());
        assertEquals(customer, argumentCaptorValue.getCustomer());
        assertNotNull(argumentCaptorValue.getCreatedDate());
    }

    @Test
    public void it_should_approved_with_multiplier_credit_application_when_customer_credit_check_limit() {
        // Given
        Long id = 12312L;

        Customer customer = new Customer();
        customer.setId(id);
        customer.setSalary(1000);

        CheckCreditLimitRequest request = new CheckCreditLimitRequest();
        request.setIdentityNumber(id);

        Credit credit = new Credit();

        CreditScoreRule creditScoreRule = new CreditScoreRule();
        creditScoreRule.setResult(CreditScoreRuleResult.MULTIPLIER);

        CreditApplicationResult savedCreditApplicationResult = new CreditApplicationResult();

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(creditService.findCreditByIdentityNumber(request.getIdentityNumber())).thenReturn(credit);
        when(creditScoreRuleService.findRule(credit, customer)).thenReturn(creditScoreRule);
        when(creditApplicationResultRepository.save(Mockito.any())).thenReturn(savedCreditApplicationResult);


        // When
        CheckCreditLimitResponse checkCreditLimitResponse = customerService.checkCreditLimit(request);

        // Then
        ArgumentCaptor<CreditApplicationResult> argumentCaptor = ArgumentCaptor.forClass(CreditApplicationResult.class);
        verify(creditApplicationResultRepository).save(argumentCaptor.capture());
        CreditApplicationResult argumentCaptorValue = argumentCaptor.getValue();
        assertTrue(argumentCaptorValue.isResult());
        assertEquals(creditScoreRule.getCreditLimit(), argumentCaptorValue.getCreditLimit());
        assertNotNull(argumentCaptorValue.getCustomer());
        assertEquals(customer, argumentCaptorValue.getCustomer());
        assertNotNull(argumentCaptorValue.getCreatedDate());
    }

    @Test
    public void it_should_approved_credit_application_when_customer_credit_check_limit() {
        // Given
        Long id = 12312L;

        Customer customer = new Customer();
        customer.setId(id);
        customer.setSalary(1000);

        CheckCreditLimitRequest request = new CheckCreditLimitRequest();
        request.setIdentityNumber(id);

        Credit credit = new Credit();

        CreditScoreRule creditScoreRule = new CreditScoreRule();
        creditScoreRule.setCreditLimit(5000.0);
        creditScoreRule.setResult(CreditScoreRuleResult.APPROVED);

        CreditApplicationResult savedCreditApplicationResult = new CreditApplicationResult();

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(creditService.findCreditByIdentityNumber(request.getIdentityNumber())).thenReturn(credit);
        when(creditScoreRuleService.findRule(credit, customer)).thenReturn(creditScoreRule);
        when(creditApplicationResultRepository.save(Mockito.any())).thenReturn(savedCreditApplicationResult);


        // When
        CheckCreditLimitResponse checkCreditLimitResponse = customerService.checkCreditLimit(request);

        // Then
        ArgumentCaptor<CreditApplicationResult> argumentCaptor = ArgumentCaptor.forClass(CreditApplicationResult.class);
        verify(creditApplicationResultRepository).save(argumentCaptor.capture());
        CreditApplicationResult argumentCaptorValue = argumentCaptor.getValue();
        assertTrue(argumentCaptorValue.isResult());
        assertEquals(creditScoreRule.getCreditLimit(), argumentCaptorValue.getCreditLimit());
        assertNotNull(argumentCaptorValue.getCustomer());
        assertEquals(customer, argumentCaptorValue.getCustomer());
        assertNotNull(argumentCaptorValue.getCreatedDate());
    }
}