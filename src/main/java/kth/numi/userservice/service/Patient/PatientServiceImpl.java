package kth.numi.userservice.service.Patient;

import kth.numi.userservice.model.Patient;
import kth.numi.userservice.model.User;
import kth.numi.userservice.repository.PatientRepository;
import kth.numi.userservice.roles.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static kth.numi.userservice.dto.UserDto.convertToDto;

@Service
public class PatientServiceImpl implements PatientService {

    final private PatientRepository patientRepository;
    final private PasswordEncoder passwordEncoder;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> getPatient(Integer id) {
        try {
            Optional<Patient> patient = patientRepository.findById(id);
            if (patient.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Could not find this patient");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertToDto(patient.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("");
        }
    }

    @Override
    public ResponseEntity<?> getAllPatients() {
        try {
            List<Patient> patients = patientRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertToDto(patients));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No patients found");
        }
    }

    @Override
    public ResponseEntity<?> savePatient(User user) {
        try {
            Patient patient = new Patient();
            patient.setFirstname(user.getFirstname());
            patient.setLastname(user.getLastname());
            patient.setAddress(user.getAddress());
            patient.setId(user.getId());
            patient.setEmail(user.getEmail());
            patient.setPassword(passwordEncoder.encode(user.getPassword()));
            patient.setPhone(user.getPhone());
            patient.setUserRole(UserRole.PATIENT);
            patientRepository.save(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(patient));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not create this patient");
        }
    }

}
