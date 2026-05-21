package com.hospital.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDto {
    private Long id;
    
    @NotBlank(message = "Provider name is required")
    private String providerName;
    
    @NotBlank(message = "Policy number is required")
    private String policyNumber;
    
    @NotNull(message = "Coverage amount is required")
    private BigDecimal coverageAmount;
    
    private Long patientId;
}
