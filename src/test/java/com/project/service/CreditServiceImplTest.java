package com.project.service;

import com.project.model.entity.Credit;
import com.project.model.exception.NotFoundException;
import com.project.repository.CreditRepository;
import com.project.service.implementations.CreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class CreditServiceImplTest {

    private CreditServiceImpl creditService;
    private CreditRepository creditRepository;
    List<Credit> credits = Arrays.asList(
            new Credit(2, 550),
            new Credit(4, 1000),
            new Credit(6, 400),
            new Credit(8, 900),
            new Credit(0, 2000));

    @BeforeEach
    void setUp() {
        creditRepository = Mockito.mock(CreditRepository.class);
        creditService = new CreditServiceImpl(creditRepository);
    }

    @Test
    public void it_should_find_credit_when_credit_by_identity_number() {
        // Given
        Long id = 2L;

        when(creditRepository.findAll()).thenReturn(credits);

        // When
        Credit credit = creditService.findCreditByIdentityNumber(id);

        // Then
        assertEquals(2, credit.getId());
        assertEquals(550,credit.getScore());
    }

    @Test
    void it_should_throw_not_found_exception_when_find_credit() {

        Long id = 1L;

        Credit credit = new Credit();
        credit.setId(Long.valueOf(id).intValue());

        Mockito.when(creditRepository.findAll()).thenReturn(credits);

        // When
        NotFoundException exception = catchThrowableOfType(() -> creditService.findCreditByIdentityNumber(id), NotFoundException.class);

        // Then
        assertEquals("Credit not found by id: " + id, exception.getMessage());
    }

}