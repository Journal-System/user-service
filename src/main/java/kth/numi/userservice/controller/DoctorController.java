package kth.numi.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kth.numi.userservice.model.User;
import kth.numi.userservice.service.Doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
@Tag(name = "Doctor Controller", description = "Manage doctor data")
public class DoctorController {
    final private DoctorService doctorService;
    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a doctor",
            description = "Get a doctor from the database")
    public ResponseEntity<?> getDoctor(@PathVariable Integer id) { return doctorService.getDoctor(id); }

    @GetMapping("/getAllDoctors")
    @Operation(summary = "Get all doctors",
            description = "Get all the doctors from the database")
    public ResponseEntity<?> getAllDoctors() { return doctorService.getAllDoctors(); }

    @PostMapping("/add")
    @Operation(summary = "Add a new doctor",
            description = "Create and save a new doctor to the database")
    public ResponseEntity<?> addDoctor(@RequestBody User user) {
        return doctorService.saveDoctor(user);
    }
}