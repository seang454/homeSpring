package kh.edu.istasd.fswdapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest (
        @NotBlank(message = "FullName is required")
        String fullName,

        @NotBlank(message = "nationalCard is required")
        String nationalCardId,

        @NotBlank(message = "segmentId is required")
        Integer segmentId,

        @NotBlank(message = "Gender is required")
        String gender,
        String email,
        String phoneNumber,
        String remark
){
}
