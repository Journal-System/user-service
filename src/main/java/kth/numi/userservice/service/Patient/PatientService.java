package kth.numi.userservice.service.Patient;

import kth.numi.userservice.model.User;
import org.springframework.http.ResponseEntity;

public interface PatientService {
    ResponseEntity<?> getPatient(Integer id);

    ResponseEntity<?> getAllPatients();

    ResponseEntity<?> savePatient(User user);
}
