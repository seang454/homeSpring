package kh.edu.istasd.fswdapi.service.impl;

import jakarta.ws.rs.core.Response;
import kh.edu.istasd.fswdapi.dto.RegisterRequest;
import kh.edu.istasd.fswdapi.dto.RegisterResponse;
import kh.edu.istasd.fswdapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final Keycloak keycloak;
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Passwords do not match");
        }
        UserRepresentation user = new UserRepresentation();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setFirstName(registerRequest.firstname());
        user.setLastName(registerRequest.lastname());

        // setPassword
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(registerRequest.password());
        user.setCredentials(List.of(cred));

        user.setEmailVerified(false);
        user.setEnabled(true);
        try (Response response = keycloak.realm("mbapi").users().create(user)){
            if (response.getStatus() == HttpStatus.CREATED.value()) {
                //verify email
                 List<UserRepresentation> userRepresentations = keycloak.realm("mbapi")
                        .users()
                        .search(user.getUsername(),true);
                 userRepresentations.stream()
                         .filter(userRepresentation -> userRepresentation.isEmailVerified().equals(false))
                         .findFirst()
                         .ifPresent(userRepresentation -> {
                             verify(userRepresentation.getId());
                         });

                return RegisterResponse.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .firstname(user.getFirstName())
                        .lastname(user.getLastName())
                        .build();
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    @Override
    public void verify(String userId) {
        UserResource userResource = keycloak.realm("mbapi").users().get(userId);
        userResource.sendVerifyEmail();

    }
}
