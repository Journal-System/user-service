package kth.numi.userservice.data;

import kth.numi.userservice.model.Staff;
import kth.numi.userservice.repository.StaffRepository;
import kth.numi.userservice.roles.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StaffDataLoader implements CommandLineRunner {

    private StaffRepository staffRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public StaffDataLoader(StaffRepository staffRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
        initializeStaffs();
    }

    @Override
    public void run(String... args) { }

    private void initializeStaffs() {
        Staff staff1 = createStaff("Alex", "Miller", "Malmo", "alex.miller@example.com", "Staff1", "1234567811");
        Staff staff2 = createStaff("Emma", "Davis", "Uppsala", "emma.davis@example.com", "Staff2", "1234567812");
        Staff staff3 = createStaff("Daniel", "Wilson", "New York", "daniel.wilson@example.com", "Staff3", "1234567813");
        Staff staff4 = createStaff("Olivia", "Brown", "Nairobi", "olivia.brown@example.com", "Staff4", "1234567814");
        Staff staff5 = createStaff("Liam", "Jones", "Alexandria", "liam.jones@example.com", "Staff5", "1234567815");
        Staff staff6 = createStaff("Ava", "Martinez", "Istanbul", "ava.martinez@example.com", "Staff6", "1234567816");

        staffRepository.save(staff1);
        staffRepository.save(staff2);
        staffRepository.save(staff3);
        staffRepository.save(staff4);
        staffRepository.save(staff5);
        staffRepository.save(staff6);
    }

    private Staff createStaff(String firstname, String lastname, String address, String email, String password, String phone) {
        Staff staff = new Staff();
        staff.setFirstname(firstname);
        staff.setLastname(lastname);
        staff.setAddress(address);
        staff.setEmail(email);
        staff.setPassword(passwordEncoder.encode(password));
        staff.setPhone(phone);
        staff.setUserRole(UserRole.STAFF);
        return staff;
    }
}