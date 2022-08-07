package com.project.service;

import com.project.model.entity.Credit;

public interface CreditService {

    Credit findCreditByIdentityNumber(Long id);
}
