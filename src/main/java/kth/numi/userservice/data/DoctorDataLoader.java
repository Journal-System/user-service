package kth.numi.userservice.data;

import kth.numi.userservice.model.Doctor;
import kth.numi.userservice.repository.DoctorRepository;
import kth.numi.userservice.roles.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DoctorDataLoader implements CommandLineRunner {
    private DoctorRepository doctorRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorDataLoader(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        initializeDoctors();
    }

    @Override
    public void run(String... args) { }

    private void initializeDoctors() {
        Doctor doctor1 = createDoctor("John", "Doe", "Stockholm", "john.doe@example.com", "Doctor1", "1234567801");
        Doctor doctor2 = createDoctor("Jane", "Smith", "London", "jane.smith@example.com", "Doctor2", "1234567802");
        Doctor doctor3 = createDoctor("Robert", "Johnson", "Madrid", "robert.johnson@example.com", "Doctor3", "1234567803");
        Doctor doctor4 = createDoctor("Emily", "Williams", "Cairo", "emily.williams@example.com", "Doctor4", "1234567804");
        Doctor doctor5 = createDoctor("Michael", "Anderson", "Barcelona", "michael.anderson@example.com", "Doctor5", "1234567805");
        Doctor doctor6 = createDoctor("Samantha", "Brown", "Amsterdam", "samantha.brown@example.com", "Doctor6", "1234567806");
        Doctor doctor7 = createDoctor("Mikael", "Söderström", "Älvsjö", "mike@kth.se", "Doctor7", "0219923934");
        Doctor doctor8 = createDoctor("Nuh", "Jama", "Hjulsta", "nuh@kth.se", "Doctor8", "0747289503");

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
        doctorRepository.save(doctor3);
        doctorRepository.save(doctor4);
        doctorRepository.save(doctor5);
        doctorRepository.save(doctor6);
        doctorRepository.save(doctor7);
        doctorRepository.save(doctor8);
    }

    private Doctor createDoctor(String firstname, String lastname, String address, String email, String password, String phone) {
        Doctor doctor = new Doctor();
        doctor.setFirstname(firstname);
        doctor.setLastname(lastname);
        doctor.setAddress(address);
        doctor.setEmail(email);
        doctor.setPassword(passwordEncoder.encode(password));
        doctor.setPhone(phone);
        doctor.setUserRole(UserRole.DOCTOR);
        return doctor;
    }
}
