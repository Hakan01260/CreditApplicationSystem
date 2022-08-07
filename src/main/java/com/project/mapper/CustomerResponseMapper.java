package com.project.mapper;

import com.project.model.api.response.CustomerResponse;
import com.project.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerResponseMapper {

    public CustomerResponse map(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getFistName(),
                customer.getLastName(),
                customer.getSalary(),
                customer.getPhoneNumber());
    }
}
