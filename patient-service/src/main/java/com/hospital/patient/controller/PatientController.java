package com.hospital.patient.controller;

import com.hospital.common.dto.PatientDto;
import com.hospital.patient.service.PatientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    
    private final PatientService patientService;
    
    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto) {
        return ResponseEntity.ok(patientService.createPatient(patientDto));
    }
    
    @GetMapping("/{id}")
    @CircuitBreaker(name = "patientService", fallbackMethod = "getPatientFallback")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<PatientDto> getPatientByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(patientService.getPatientByUserId(userId));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Long id, @RequestBody PatientDto patientDto) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
    
    public ResponseEntity<PatientDto> getPatientFallback(Long id, Exception e) {
        PatientDto fallback = PatientDto.builder()
                .id(id)
                .name("Service Unavailable")
                .email("fallback@example.com")
                .build();
        return ResponseEntity.ok(fallback);
    }
}
