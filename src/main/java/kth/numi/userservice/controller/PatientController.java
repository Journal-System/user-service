package kth.numi.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kth.numi.userservice.model.User;
import kth.numi.userservice.service.Patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
@Tag(name = "Patient Controller", description = "Manage patient data")
public class PatientController {

    final private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a patient",
            description = "Get a patient from the database")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return patientService.getPatient(id);
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new patient",
            description = "Create and save a new patient to the database")
    public ResponseEntity<?> addPatient(@RequestBody User user) {
        return patientService.savePatient(user);
    }

    @GetMapping("/getAllPatients")
    @Operation(summary = "Get all patients",
            description = "Get all patients from the database")
    public ResponseEntity<?> getAllPatients() {
        return patientService.getAllPatients();
    }
}