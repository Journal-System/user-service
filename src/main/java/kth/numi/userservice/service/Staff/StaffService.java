package kth.numi.userservice.service.Staff;

import kth.numi.userservice.model.User;
import org.springframework.http.ResponseEntity;

public interface StaffService {

    ResponseEntity<?> getStaff(Integer id);
    ResponseEntity<?> getAllStaffs();
    ResponseEntity<?> saveStaff(User user);
}
