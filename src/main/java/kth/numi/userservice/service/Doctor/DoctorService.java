package kth.numi.userservice.service.Doctor;

import kth.numi.userservice.model.User;
import org.springframework.http.ResponseEntity;

public interface DoctorService {
    ResponseEntity<?> getDoctor(Integer id);
    ResponseEntity<?> getAllDoctors();
    ResponseEntity<?> saveDoctor(User user);
}
