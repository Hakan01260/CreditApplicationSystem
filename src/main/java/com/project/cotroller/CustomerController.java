package com.project.cotroller;

import com.project.model.api.request.CheckCreditLimitRequest;
import com.project.model.api.request.CustomerCreateRequest;
import com.project.model.api.request.CustomerUpdateRequest;
import com.project.model.api.response.CheckCreditLimitResponse;
import com.project.model.api.response.CustomerResponse;
import com.project.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping
    @ApiOperation(value = "New Customer adding method")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerCreateRequest request) {
        return new ResponseEntity<>(customerService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.deleteById(id);
    }

    @PutMapping
    public CustomerResponse updateCustomer(@RequestBody CustomerUpdateRequest request) {
        return customerService.update(request);
    }

    @GetMapping("/count")
    public int getCustomerCount() {
        return customerService.getNumberOfCustomerNumber();
    }

    @PostMapping("/checkCreditLimit")
    public CheckCreditLimitResponse getCheckCreditLimit(@RequestBody CheckCreditLimitRequest request)  {
        return customerService.checkCreditLimit(request);
    }
}
