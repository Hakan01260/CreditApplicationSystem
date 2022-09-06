package com.project.model.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditApplicationResultResponse {

    private Long id;

    private boolean result;

    private Double creditLimit;

    private Date createdDate;

    private Long customerId;

    public CreditApplicationResultResponse(Long id, boolean result, Double creditLimit, Date createdDate, Long customerId) {
        this.id = id;
        this.result = result;
        this.creditLimit = creditLimit;
        this.createdDate = createdDate;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
