package kth.numi.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kth.numi.userservice.model.User;
import kth.numi.userservice.service.KeyCloak.KeyCloakService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "KeyCloak Controller", description = "Manage Keycloak")
public class KeyCloakController {
    KeyCloakService keyCloakService;

    @Autowired
    public KeyCloakController(KeyCloakService keyCloakService) {
        this.keyCloakService = keyCloakService;
    }

    @PostMapping
    @Operation(summary = "Add a user",
            description = "Add a user to keycloak")
    public ResponseEntity<?> addUser(@RequestBody User user) throws Exception {
        return keyCloakService.addUser(user);
    }

    @GetMapping("/{userName}")
    @Operation(summary = "Get a user",
            description = "Get a user from keycloak")
    public List<UserRepresentation> getUser(@PathVariable("userName") String userName) {
        List<UserRepresentation> user = keyCloakService.getUser(userName);
        return user;
    }

    @PutMapping("/update/{userId}")
    @Operation(summary = "Update a user",
            description = "Update a user in keycloak")
    public String updateUser(@PathVariable("userId") String userId,
                             @RequestBody User user) {
        keyCloakService.updateUser(userId, user);
        return "User Details Updated Successfully";
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a user",
            description = "Delete a user from keycloak")
    public String deleteUser(@PathVariable("userId") String userId) {
        keyCloakService.deleteUser(userId);
        return "User Deleted Successfully";
    }
}
