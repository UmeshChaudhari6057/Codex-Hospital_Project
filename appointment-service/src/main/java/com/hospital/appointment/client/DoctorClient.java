package com.hospital.appointment.client;

import com.hospital.common.dto.DoctorDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service")
@CircuitBreaker(name = "doctorService", fallbackMethod = "getDoctorFallback")
public interface DoctorClient {
    
    @GetMapping("/doctors/{id}")
    DoctorDto getDoctorById(@PathVariable("id") Long id);
    
    default DoctorDto getDoctorFallback(Long id, Exception e) {
        return DoctorDto.builder()
                .id(id)
                .name("Service Unavailable")
                .email("fallback@example.com")
                .build();
    }
}
