package kh.edu.istasd.fswdapi.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String fullName,
        String email,
        String phoneNumber
) {

}
