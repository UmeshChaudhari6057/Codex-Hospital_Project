package com.hospital.doctor.controller;

import com.hospital.common.dto.DoctorDto;
import com.hospital.doctor.service.DoctorService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    
    private final DoctorService doctorService;
    
    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.createDoctor(doctorDto));
    }
    
    @GetMapping("/{id}")
    @CircuitBreaker(name = "doctorService", fallbackMethod = "getDoctorFallback")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<DoctorDto> getDoctorByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(doctorService.getDoctorByUserId(userId));
    }
    
    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
    
    public ResponseEntity<DoctorDto> getDoctorFallback(Long id, Exception e) {
        DoctorDto fallback = DoctorDto.builder()
                .id(id)
                .name("Service Unavailable")
                .email("fallback@example.com")
                .build();
        return ResponseEntity.ok(fallback);
    }
}
