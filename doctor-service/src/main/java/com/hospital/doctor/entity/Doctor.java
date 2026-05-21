package com.hospital.doctor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "doctors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 100)
    private String specialization;
    
    @Column(unique = true, length = 100)
    private String email;
    
    @ElementCollection
    @CollectionTable(name = "doctor_departments", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "department_name")
    private Set<String> departments;
}
