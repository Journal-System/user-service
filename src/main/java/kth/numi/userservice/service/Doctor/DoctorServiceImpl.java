package kth.numi.userservice.service.Doctor;

import kth.numi.userservice.model.Doctor;
import kth.numi.userservice.model.User;
import kth.numi.userservice.repository.DoctorRepository;
import kth.numi.userservice.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static kth.numi.userservice.dto.UserDto.convertToDto;

@Service
public class DoctorServiceImpl implements DoctorService{
    final private DoctorRepository doctorRepository;
    final private PasswordEncoder passwordEncoder;
    @Autowired
    public DoctorServiceImpl (DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public ResponseEntity<?> getDoctor(Integer id) {
        try {
            Optional<Doctor> doctor = doctorRepository.findById(id);
            if (doctor.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find this doctor");
            }
            return ResponseEntity.status(HttpStatus.OK).body(convertToDto(doctor.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }

    @Override
    public ResponseEntity<?> getAllDoctors() {
        try {
            List<Doctor> doctors = doctorRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(convertToDto(doctors));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No doctors found");
        }
    }

    @Override
    public ResponseEntity<?> saveDoctor(User user) {
        try {
            Doctor doctor = new Doctor();
            doctor.setFirstname(user.getFirstname());
            doctor.setLastname(user.getLastname());
            doctor.setAddress(user.getAddress());
            doctor.setId(user.getId());
            doctor.setEmail(user.getEmail());
            doctor.setPassword(passwordEncoder.encode(user.getPassword()));
            doctor.setPhone(user.getPhone());
            doctor.setRole(Role.DOCTOR);
            doctorRepository.save(doctor);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(doctor));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not create this doctor");
        }
    }
}
