package com.project.model.api.response;

public class CheckCreditLimitResponse {

    private Long id;
    private boolean result;
    private Double creditLimit;

    public CheckCreditLimitResponse(Long id, boolean result, Double creditLimit) {
        this.id = id;
        this.result = result;
        this.creditLimit = creditLimit;
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
}
