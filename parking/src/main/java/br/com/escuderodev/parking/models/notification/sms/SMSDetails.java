package br.com.escuderodev.parking.models.notification.sms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SMSDetails {
    private String to;       // Número de telefone de destino
    private String message;  // Mensagem de texto a ser enviada
}
