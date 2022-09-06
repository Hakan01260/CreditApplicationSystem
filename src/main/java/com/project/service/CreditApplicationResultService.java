package com.project.service;

import com.project.model.api.response.CreditApplicationResultResponse;
import com.project.model.entity.CreditApplicationResult;

public interface CreditApplicationResultService {

    CreditApplicationResultResponse findById(Long id);
}
