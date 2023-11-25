package kth.numi.userservice.service.Authentication;

import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> authenticateUser(String email, String password);
}
