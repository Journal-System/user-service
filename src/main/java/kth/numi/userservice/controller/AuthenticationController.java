package kth.numi.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kth.numi.userservice.service.Authentication.AuthenticationService;
import kth.numi.userservice.service.KeyCloak.KeyCloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@Tag(name = "Authentication Controller", description = "Manage authentication of the users")
public class AuthenticationController {
    final private AuthenticationService authenticationService;
    final private KeyCloakService keyCloakService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, KeyCloakService keyCloakService) {
        this.authenticationService = authenticationService;
        this.keyCloakService = keyCloakService;
    }
    @PostMapping("/login")
    @Operation(summary = "User login",
            description = "Authentication a user")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        String access_token = (String) keyCloakService.getToken(email, password).getBody();
        return authenticationService.authenticateUser(email, password, access_token);
    }
}