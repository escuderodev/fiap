package br.com.escuderodev.parking.models.parking;

import java.time.LocalDateTime;

public class TimeServer {
    private LocalDateTime timeServer;

    public LocalDateTime getTimeServer() {

        return timeServer = LocalDateTime.now();
    }

}
