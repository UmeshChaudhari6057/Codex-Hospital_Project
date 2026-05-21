package com.hospital.appointment.service;

import com.hospital.common.event.AppointmentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    
    private final KafkaTemplate<String, AppointmentEvent> kafkaTemplate;
    
    public void publishAppointmentEvent(AppointmentEvent event) {
        log.info("Publishing appointment event: {}", event);
        kafkaTemplate.send("appointment-events", event);
    }
}
