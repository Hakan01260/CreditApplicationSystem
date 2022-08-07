package com.project.model.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditResponse {

    private Long id;

    private boolean creditResult;

    private Long creditScore;

    public CreditResponse(Long id, boolean creditResult, Long creditScore) {
        this.id = id;
        this.creditResult = creditResult;
        this.creditScore = creditScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCreditResult() {
        return creditResult;
    }

    public void setCreditResult(boolean creditResult) {
        this.creditResult = creditResult;
    }

    public Long getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Long creditScore) {
        this.creditScore = creditScore;
    }
}
