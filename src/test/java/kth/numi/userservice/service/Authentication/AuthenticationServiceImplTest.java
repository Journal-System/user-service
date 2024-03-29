package kth.numi.userservice.service.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import kth.numi.userservice.dto.AuthenticationDto;
import kth.numi.userservice.model.*;
import kth.numi.userservice.repository.AuthenticationRepository;
import kth.numi.userservice.roles.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static kth.numi.userservice.dto.AuthenticationDto.convertToDto;

@WebMvcTest(AuthenticationServiceImpl.class)
class AuthenticationServiceImplTest {
    @Autowired private AuthenticationServiceImpl authenticationServiceImpl;
    @MockBean AuthenticationRepository authenticationRepository;
    @MockBean PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        authenticationServiceImpl = new AuthenticationServiceImpl(authenticationRepository, passwordEncoder);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToAuthenticateUserThatDoNotExist() {
        when(authenticationRepository.findByEmail("numoh.55@gmail.com")).thenReturn(Optional.empty());

        ResponseEntity<?> result = authenticationServiceImpl.authenticateUser("numoh.55@gmail.com", "Password123", "123123");
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals("Invalid credentials", result.getBody());
    }

    @Test
    void testToAuthenticateUserThatDoExist() {
        User mockUser = User.builder()
                .id(1)
                .address("Stockholm")
                .email("admin@example.com")
                .firstname("Admin")
                .lastname("Admin")
                .password(passwordEncoder.encode("PASSWORD123"))
                .phone("0769348121")
                .role(Role.PATIENT)
                .build();

        when(authenticationRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(mockUser));
        ResponseEntity<?> result = authenticationServiceImpl.authenticateUser("admin@example.com", "PASSWORD123", "123123");
        AuthenticationDto resultUser = (AuthenticationDto) result.getBody();

        assertNotNull(result, "Result should not be null if patient is found");
        assertEquals(result.getBody(), convertToDto(mockUser, "123123"));
        assertEquals(resultUser.getId(), mockUser.getId());
        assertEquals(resultUser.getRole(), mockUser.getRole());
        assertEquals(resultUser.getAccess_token(), "123123");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testToAuthenticateUserButInternalServerError() {
        when(authenticationRepository.findByEmail("admin@example.com")).thenThrow(new RuntimeException("Simulated Internal Server Error"));

        ResponseEntity<?> result = authenticationServiceImpl.authenticateUser("admin@example.com", "PASSWORD123", "123123");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals(result.getBody(), "An error occurred");
    }
}