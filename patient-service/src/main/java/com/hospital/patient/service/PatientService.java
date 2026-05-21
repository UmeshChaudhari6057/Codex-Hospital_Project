package com.hospital.patient.service;

import com.hospital.common.dto.InsuranceDto;
import com.hospital.common.dto.PatientDto;
import com.hospital.patient.entity.Insurance;
import com.hospital.patient.entity.Patient;
import com.hospital.patient.repository.InsuranceRepository;
import com.hospital.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientService {
    
    private final PatientRepository patientRepository;
    private final InsuranceRepository insuranceRepository;
    private final ModelMapper modelMapper;
    
    @Transactional
    public PatientDto createPatient(PatientDto patientDto) {
        if (patientRepository.existsByEmail(patientDto.getEmail())) {
            throw new RuntimeException("Patient with this email already exists");
        }
        
        Patient patient = modelMapper.map(patientDto, Patient.class);
        patient.setUserId(patientDto.getUserId());
        
        if (patientDto.getInsurance() != null) {
            Insurance insurance = modelMapper.map(patientDto.getInsurance(), Insurance.class);
            insurance = insuranceRepository.save(insurance);
            patient.setInsurance(insurance);
        }
        
        Patient savedPatient = patientRepository.save(patient);
        return modelMapper.map(savedPatient, PatientDto.class);
    }
    
    public PatientDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        return modelMapper.map(patient, PatientDto.class);
    }
    
    public PatientDto getPatientByUserId(Long userId) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Patient not found for user id: " + userId));
        return modelMapper.map(patient, PatientDto.class);
    }
    
    @Transactional
    public PatientDto updatePatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        
        patient.setName(patientDto.getName());
        patient.setBirthDate(patientDto.getBirthDate());
        patient.setGender(patientDto.getGender());
        if (patientDto.getBloodGroup() != null) {
            patient.setBloodGroup(Patient.BloodGroup.valueOf(patientDto.getBloodGroup()));
        }
        
        if (patientDto.getInsurance() != null) {
            if (patient.getInsurance() != null) {
                Insurance insurance = modelMapper.map(patientDto.getInsurance(), Insurance.class);
                insurance.setId(patient.getInsurance().getId());
                insurance = insuranceRepository.save(insurance);
                patient.setInsurance(insurance);
            } else {
                Insurance insurance = modelMapper.map(patientDto.getInsurance(), Insurance.class);
                insurance = insuranceRepository.save(insurance);
                patient.setInsurance(insurance);
            }
        }
        
        Patient updatedPatient = patientRepository.save(patient);
        return modelMapper.map(updatedPatient, PatientDto.class);
    }
    
    @Transactional
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        patientRepository.delete(patient);
    }
}
