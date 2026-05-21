package com.hospital.common.event;

import com.hospital.common.dto.AppointmentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data       
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEvent {
    private Long appointmentId;
    private String eventType; // CREATED, CANCELLED, COMPLETED
    private AppointmentDto appointment;
    private LocalDateTime timestamp;
    private Long patientId;
    private Long doctorId;
}
