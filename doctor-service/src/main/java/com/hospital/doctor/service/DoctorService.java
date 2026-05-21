package com.hospital.doctor.service;

import com.hospital.common.dto.DoctorDto;
import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    
    @Transactional
    public DoctorDto createDoctor(DoctorDto doctorDto) {
        if (doctorRepository.existsByEmail(doctorDto.getEmail())) {
            throw new RuntimeException("Doctor with this email already exists");
        }
        
        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
        doctor.setUserId(doctorDto.getUserId());
        doctor.setDepartments(doctorDto.getDepartments());
        
        Doctor savedDoctor = doctorRepository.save(doctor);
        return modelMapper.map(savedDoctor, DoctorDto.class);
    }
    
    public DoctorDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        return modelMapper.map(doctor, DoctorDto.class);
    }
    
    public DoctorDto getDoctorByUserId(Long userId) {
        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Doctor not found for user id: " + userId));
        return modelMapper.map(doctor, DoctorDto.class);
    }

    public List<DoctorDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public DoctorDto updateDoctor(Long id, DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        
        doctor.setName(doctorDto.getName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        if (doctorDto.getDepartments() != null) {
            doctor.setDepartments(doctorDto.getDepartments());
        }
        
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return modelMapper.map(updatedDoctor, DoctorDto.class);
    }
    
    @Transactional
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        doctorRepository.delete(doctor);
    }
}
