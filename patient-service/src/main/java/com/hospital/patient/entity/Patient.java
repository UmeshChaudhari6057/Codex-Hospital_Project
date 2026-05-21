package com.hospital.patient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 40)
    private String name;
    
    @Column(nullable = false)
    private LocalDate birthDate;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String gender;
    
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    
    @Column(name = "user_id")
    private Long userId;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;
    
    public enum BloodGroup {
        A_POSITIVE, A_NEGATIVE, B_POSITIVE, B_NEGATIVE,
        AB_POSITIVE, AB_NEGATIVE, O_POSITIVE, O_NEGATIVE
    }
}
