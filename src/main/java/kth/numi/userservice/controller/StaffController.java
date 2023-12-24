package kth.numi.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kth.numi.userservice.model.User;
import kth.numi.userservice.service.Staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
@Tag(name = "Staff Controller", description = "Manage staff data")
public class StaffController {
    private final StaffService staffService;
    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new staff",
            description = "Create and save a new user to the database")
    public ResponseEntity<?> add(@RequestBody User user) {
        return staffService.saveStaff(user);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get a staff",
            description = "Get a staff from the database")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return staffService.getStaff(id);
    }

    @GetMapping("/getAllStaffs")
    @PreAuthorize("hasRole('PATIENT')")
    @Operation(summary = "Get all staffs",
            description = "Get all the staffs from the database")
    public ResponseEntity<?> getAllStaffs() {
        return staffService.getAllStaffs();
    }
}