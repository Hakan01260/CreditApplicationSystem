package com.project.cotroller;

import com.project.model.api.response.CreditApplicationResultResponse;
import com.project.service.CreditApplicationResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/credit-application-result")
public class CreditApplicationResultController {

    private final CreditApplicationResultService creditApplicationService;

    @GetMapping("/{id}")
    public CreditApplicationResultResponse getCreditApplicationResult(@PathVariable Long id) {
        return creditApplicationService.findById(id);
    }

}
