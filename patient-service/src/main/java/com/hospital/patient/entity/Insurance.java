package com.hospital.patient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "insurance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String providerName;
    
    @Column(unique = true, nullable = false)
    private String policyNumber;
    
    @Column(nullable = false)
    private BigDecimal coverageAmount;
    
    @Column(name = "patient_id")
    private Long patientId;
}
