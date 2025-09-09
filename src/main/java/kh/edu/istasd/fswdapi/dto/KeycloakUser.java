package kh.edu.istasd.fswdapi.dto;

import lombok.Builder;

@Builder
public record KeycloakUser(
        long createdTimestamp,
        String email,
        boolean emailVerified,
        boolean enabled,
        String firstName,
        String id,
        String lastName,
        int notBefore,
        boolean totp,
        String username
) {
}
