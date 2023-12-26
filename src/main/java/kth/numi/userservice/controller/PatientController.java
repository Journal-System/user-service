package kth.numi.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kth.numi.userservice.model.User;
import kth.numi.userservice.service.KeyCloak.KeyCloakService;
import kth.numi.userservice.service.Patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
@Tag(name = "Patient Controller", description = "Manage patient data")
public class PatientController {
    final private PatientService patientService;
    final private KeyCloakService keyCloakService;
    @Autowired
    public PatientController(PatientService patientService, KeyCloakService keyCloakService) {
        this.patientService = patientService;
        this.keyCloakService = keyCloakService;
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    @Operation(summary = "Get a patient",
            description = "Get a patient from the database")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return patientService.getPatient(id);
    }

    @GetMapping("/getAllPatients")
    @PreAuthorize("hasAnyRole('DOCTOR', 'STAFF')")
    @Operation(summary = "Get all patients",
            description = "Get all patients from the database")
    public ResponseEntity<?> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new patient",
            description = "Create and save a new patient to the database")
    public ResponseEntity<?> addPatient(@RequestBody User user) throws Exception {
        try {
            keyCloakService.addUser(user);
            return patientService.savePatient(user);
        } catch (Exception e) {
            throw new Exception("Could not add the user to keycloak/database");
        }
    }
}