package kth.numi.userservice.service.Authentication;

import kth.numi.userservice.model.User;
import kth.numi.userservice.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import static kth.numi.userservice.dto.AuthenticationDto.convertToDto;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthenticationServiceImpl(AuthenticationRepository authenticationRepository, PasswordEncoder passwordEncoder) {
        this.authenticationRepository = authenticationRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public ResponseEntity<?> authenticateUser(String email, String password, String access_token) {
        try {
            Optional<User> foundUser = authenticationRepository.findByEmail(email);
            if (foundUser.isPresent() && passwordEncoder.matches(password, foundUser.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToDto(foundUser.get(), access_token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid credentials");
            }
        } catch (Exception e) {
            // print the stack trace of the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }
}