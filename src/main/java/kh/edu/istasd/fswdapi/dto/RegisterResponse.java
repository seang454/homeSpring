package kh.edu.istasd.fswdapi.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String username,
        String email,
        String firstname,
        String lastname
) {
}
