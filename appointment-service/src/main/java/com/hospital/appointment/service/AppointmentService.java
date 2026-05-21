package com.hospital.appointment.service;

import com.hospital.appointment.client.DoctorClient;
import com.hospital.appointment.client.PatientClient;
import com.hospital.appointment.entity.Appointment;
import com.hospital.appointment.repository.AppointmentRepository;
import com.hospital.common.dto.AppointmentDto;
import com.hospital.common.dto.DoctorDto;
import com.hospital.common.dto.PatientDto;
import com.hospital.common.event.AppointmentEvent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;
    private final KafkaProducerService kafkaProducerService;
    private final ModelMapper modelMapper;
    
    @Transactional
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        // Validate patient exists
        PatientDto patient = patientClient.getPatientById(appointmentDto.getPatientId());
        if (patient == null || "Service Unavailable".equals(patient.getName())) {
            throw new RuntimeException("Patient not found or service unavailable");
        }
        
        // Validate doctor exists
        DoctorDto doctor = doctorClient.getDoctorById(appointmentDto.getDoctorId());
        if (doctor == null || "Service Unavailable".equals(doctor.getName())) {
            throw new RuntimeException("Doctor not found or service unavailable");
        }
        
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        appointment.setStatus(Appointment.Status.SCHEDULED);
        
        Appointment savedAppointment = appointmentRepository.save(appointment);
        
        // Publish event to Kafka
        AppointmentEvent event = AppointmentEvent.builder()
                .appointmentId(savedAppointment.getId())
                .eventType("CREATED")
                .appointment(modelMapper.map(savedAppointment, AppointmentDto.class))
                .timestamp(LocalDateTime.now())
                .patientId(patient.getId())
                .doctorId(doctor.getId())
                .build();
        kafkaProducerService.publishAppointmentEvent(event);
        
        return modelMapper.map(savedAppointment, AppointmentDto.class);
    }
    
    public AppointmentDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        return modelMapper.map(appointment, AppointmentDto.class);
    }
    
    @Transactional
    public AppointmentDto cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        
        appointment.setStatus(Appointment.Status.CANCELLED);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        
        // Publish event to Kafka
        AppointmentEvent event = AppointmentEvent.builder()
                .appointmentId(updatedAppointment.getId())
                .eventType("CANCELLED")
                .appointment(modelMapper.map(updatedAppointment, AppointmentDto.class))
                .timestamp(LocalDateTime.now())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .build();
        kafkaProducerService.publishAppointmentEvent(event);
        
        return modelMapper.map(updatedAppointment, AppointmentDto.class);
    }
    
    @Transactional
    public AppointmentDto completeAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        
        appointment.setStatus(Appointment.Status.COMPLETED);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        
        // Publish event to Kafka
        AppointmentEvent event = AppointmentEvent.builder()
                .appointmentId(updatedAppointment.getId())
                .eventType("COMPLETED")
                .appointment(modelMapper.map(updatedAppointment, AppointmentDto.class))
                .timestamp(LocalDateTime.now())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .build();
        kafkaProducerService.publishAppointmentEvent(event);
        
        return modelMapper.map(updatedAppointment, AppointmentDto.class);
    }
}
