package com.project.service.implementations;

import com.project.model.entity.Credit;
import com.project.model.exception.NotFoundException;
import com.project.repository.CreditRepository;
import com.project.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    public Credit findCreditByIdentityNumber(Long id){
        int mod = (int) (id % 10);
        List<Credit> credits = creditRepository.findAll();
        return credits
                .stream()
                .filter(cr -> cr.getId() == mod)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Credit not found by id: " + id));
    }
}
