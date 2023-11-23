package kth.numi.userservice.service.User;

import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> getUser(Integer id);
    ResponseEntity<?> getAllUsers();
}
