package br.com.escuderodev.parking.models.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailDetails {
    private String recipient;
    private String subject;
    private String messageBody;
    private String attachment;
}
