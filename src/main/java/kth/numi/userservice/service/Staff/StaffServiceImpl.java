package kth.numi.userservice.service.Staff;

import kth.numi.userservice.model.Staff;
import kth.numi.userservice.model.User;
import kth.numi.userservice.repository.StaffRepository;
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
public class StaffServiceImpl implements StaffService {
    final private StaffRepository staffRepository;
    final private PasswordEncoder passwordEncoder;
    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> getStaff(Integer id) {
        try {
            Optional<Staff> staff = staffRepository.findById(id);
            if (staff.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find this staff");
            }
            return ResponseEntity.status(HttpStatus.OK).body(convertToDto(staff.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<?> getAllStaffs() {
        try {
            List<Staff> staffs = staffRepository.findAll();
            if (!staffs.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(convertToDto(staffs));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No staffs found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<?> saveStaff(User user) {
        try {
            Staff staff = new Staff();
            staff.setFirstname(user.getFirstname());
            staff.setLastname(user.getLastname());
            staff.setAddress(user.getAddress());
            staff.setId(user.getId());
            staff.setEmail(user.getEmail());
            staff.setPassword(passwordEncoder.encode(user.getPassword()));
            staff.setPhone(user.getPhone());
            staff.setRole(Role.STAFF);
            staffRepository.save(staff);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(staff));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not create this staff");
        }
    }
}