package kth.numi.userservice.service.KeyCloak;

import kth.numi.userservice.model.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KeyCloakService {
    ResponseEntity<?> addUser(User user) throws Exception;
    List<UserRepresentation> getUser(String userName);
    void updateUser(String userId, User user);
    void deleteUser(String userId);
    ResponseEntity<?> getToken(String email, String password);
}
