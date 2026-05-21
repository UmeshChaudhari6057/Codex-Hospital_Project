package com.hospital.appointment.client;

import com.hospital.common.dto.PatientDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
@CircuitBreaker(name = "patientService", fallbackMethod = "getPatientFallback")
public interface PatientClient {
    
    @GetMapping("/patients/{id}")
    PatientDto getPatientById(@PathVariable("id") Long id);
    
    default PatientDto getPatientFallback(Long id, Exception e) {
        return PatientDto.builder()
                .id(id)
                .name("Service Unavailable")
                .email("fallback@example.com")
                .build();
    }
}
