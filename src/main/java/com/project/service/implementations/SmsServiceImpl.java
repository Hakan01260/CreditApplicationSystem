package com.project.service.implementations;

import com.project.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    public void sendSms(String phoneNumber, String message){
    }
}
