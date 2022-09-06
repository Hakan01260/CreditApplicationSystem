package com.project.service.implementations;

import com.project.model.api.response.CreditApplicationResultResponse;
import com.project.model.entity.CreditApplicationResult;
import com.project.model.exception.NotFoundException;
import com.project.repository.CreditApplicationResultRepository;
import com.project.service.CreditApplicationResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CreditApplicationResultServiceImpl implements CreditApplicationResultService {

    private final CreditApplicationResultRepository repository;

    @Transactional(readOnly = true)
    public CreditApplicationResultResponse findById(Long id){
        Optional<CreditApplicationResult> optionalCreditApplicationResult = repository.findById(id);
        if (!optionalCreditApplicationResult.isPresent()){
            throw new NotFoundException("CreditApplicationResult not found by id :" + id);
        }
        CreditApplicationResult creditApplicationResult = optionalCreditApplicationResult.get();
        return new CreditApplicationResultResponse(
                creditApplicationResult.getId(),
                creditApplicationResult.isResult(),
                creditApplicationResult.getCreditLimit(),
                creditApplicationResult.getCreatedDate(),
                creditApplicationResult.getCustomer().getId());
    }
}
