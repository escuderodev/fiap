package br.com.escuderodev.parking.controllers;

import br.com.escuderodev.parking.models.notification.sms.SMSDetails;
import br.com.escuderodev.parking.services.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSController {

    private final SMSService smsService;

    @Autowired
    public SMSController(SMSService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send-sms")
    public void sendSMS(@RequestBody SMSDetails request) {
        smsService.sendSMS(request.getTo(), request.getMessage());
    }
}