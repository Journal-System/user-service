package kth.numi.userservice.controller;

import kth.numi.userservice.model.User;
import kth.numi.userservice.roles.Role;
import kth.numi.userservice.service.Authentication.AuthenticationService;
import kth.numi.userservice.service.KeyCloak.KeyCloakServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean private AuthenticationService authenticationService;
    @MockBean private KeyCloakServiceImpl keyCloakServiceImpl;

    @Test
    void testToLoginUsingValidEmailAndPassword() throws Exception {

        User mockUser = createUser();

        given(authenticationService.authenticateUser("test@test.com", "PASSWORD123", "123123"))
                .willAnswer(invocation -> ResponseEntity.ok(mockUser));

        given(keyCloakServiceImpl.getToken("test@test.com", "PASSWORD123"))
                .willAnswer(invocation -> ResponseEntity.ok("123123"));

        mvc.perform(post("/authentication/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .param("email", "test@test.com")
                        .param("password", "PASSWORD123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("firstname").value("Test"))
                .andExpect(jsonPath("lastname").value("Test"))
                .andExpect(jsonPath("email").value("test@test.com"))
                .andExpect(jsonPath("password").value("PASSWORD123"))
                .andExpect(jsonPath("address").value("Stockholm"))
                .andExpect(jsonPath("phone").value("0769310491"))
                .andExpect(jsonPath("role").value("PATIENT"))
                .andReturn();

        verify(authenticationService, times(1)).authenticateUser("test@test.com", "PASSWORD123", "123123");
    }

    @Test
    void testToLoginUsingInvalidEmailAndPassword() throws Exception {

        given(authenticationService.authenticateUser("test@test.com", "PASSWORD123", "123123"))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));

        given(keyCloakServiceImpl.getToken("test@test.com", "PASSWORD123"))
                .willAnswer(invocation -> ResponseEntity.ok("123123"));

        mvc.perform(post("/authentication/login")
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(MediaType.TEXT_PLAIN)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .param("email", "test@test.com")
                        .param("password", "PASSWORD123"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"))
                .andExpect(content().string("Invalid credentials"))
                .andReturn();

        verify(authenticationService, times(1)).authenticateUser("test@test.com", "PASSWORD123", "123123");
    }
    private User createUser() {
        User user = new User();
        user.setId(1);
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setEmail("test@test.com");
        user.setPassword("PASSWORD123");
        user.setAddress("Stockholm");
        user.setPhone("0769310491");
        user.setRole(Role.PATIENT);
        return user;
    }
}