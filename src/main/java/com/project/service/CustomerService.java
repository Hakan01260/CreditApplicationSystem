package com.project.service;

import com.project.model.api.request.CheckCreditLimitRequest;
import com.project.model.api.request.CustomerCreateRequest;
import com.project.model.api.request.CustomerUpdateRequest;
import com.project.model.api.response.CheckCreditLimitResponse;
import com.project.model.api.response.CustomerResponse;
import com.project.model.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService  {

    List<CustomerResponse> findAll();

    CustomerResponse findById(Long id);

    CustomerResponse create(CustomerCreateRequest request);

    void deleteById(Long id);

    CustomerResponse update(CustomerUpdateRequest request);

    int getNumberOfCustomerNumber();

    CheckCreditLimitResponse checkCreditLimit(CheckCreditLimitRequest request);
}
