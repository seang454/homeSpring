package kh.edu.istasd.fswdapi.dto;

public record RegisterRequest(
        String username,
        String email,
        String firstname,
        String lastname,
        String password,
        String confirmedPassword
) {
}
