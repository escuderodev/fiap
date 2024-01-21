package br.com.escuderodev.parking.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class SMSService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String phoneNumber;

    public void sendSMS(String to, String message) {
        System.out.println("Enviando SMS...");

        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(phoneNumber),
                message
        ).create();
    }

    public void sendSMSScheduled(String to, String message, Long timeScheduled) {
        System.out.println("Enviando SMS timeScheduled..." + timeScheduled);

        Twilio.init(accountSid, authToken);

        final var scheduled = this.createScheduledTime(timeScheduled);

        Message.creator(
                new PhoneNumber(to),
                "MGf37456fc32cf0dcf258f3aa0f5c14808",
                message
        )
                .setSendAt(ZonedDateTime.of(scheduled, ZoneId.of("America/Sao_Paulo")))
                .setScheduleType(Message.ScheduleType.FIXED)
                .create();
    }

    private LocalDateTime createScheduledTime(Long timeScheduled) {
        final var dateTimeNow = LocalDateTime.now();
        final var durationInMinutes = timeScheduled;

        return dateTimeNow.plusMinutes(durationInMinutes - 44);
    }
}
