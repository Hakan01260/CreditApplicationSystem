package com.project.service;

import com.project.model.entity.CreditApplicationResult;
import com.project.model.entity.Customer;
import com.project.model.exception.NotFoundException;
import com.project.repository.CreditApplicationResultRepository;
import com.project.service.implementations.CreditApplicationResultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CreditApplicationResultServiceImplTest {

    private CreditApplicationResultServiceImpl creditApplicationResultService;
    private CreditApplicationResultRepository repository;


    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CreditApplicationResultRepository.class);
        creditApplicationResultService = new CreditApplicationResultServiceImpl(repository);
    }

    @Test
    public void it_should_throw_not_found_exception_when_credit_application_result_not_found() {
        // Given

        Long id = 12312L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        NotFoundException exception = catchThrowableOfType(() -> creditApplicationResultService.findById(id), NotFoundException.class);

        // Then
        assertEquals("CreditApplicationResult not found by id :" + id, exception.getMessage());
        verify(repository, times(1)).findById(Mockito.any());
    }

    @Test
    public void it_should_find_credit_application_result_when_credit_application_result_is_exists(){
        // Given
        Long id = 12312L;

        CreditApplicationResult creditApplicationResult = new CreditApplicationResult();
        creditApplicationResult.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(creditApplicationResult));

        // When
        creditApplicationResultService.findById(id);

        // Then
        assertEquals(id, creditApplicationResult.getId());
        verify(repository, times(1)).findById(id);
    }
}