package kth.numi.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kth.numi.userservice.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Manage user data")
public class UserController {

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a user",
            description = "Get a user from the database")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @GetMapping("/getAllUsers")
    @Operation(summary = "Get all users",
            description = "Get all the users from the database")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }
}
