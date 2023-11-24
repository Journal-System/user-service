package kth.numi.userservice.data;

import kth.numi.userservice.model.Patient;
import kth.numi.userservice.model.User;
import kth.numi.userservice.repository.PatientRepository;
import kth.numi.userservice.roles.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PatientDataLoader implements CommandLineRunner {
    private PatientRepository patientRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PatientDataLoader(PatientRepository patientRepository,
                             PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        initializePatients();
    }
    @Override
    public void run(String... args) { }

    private void initializePatients() {
        Patient patient1 = createPatient("Alice", "Johnson", "123 Main Street", "alice@example.com", "Password1", "1234567890");
        Patient patient2 = createPatient("Bob", "Smith", "456 Oak Avenue", "bob@example.com", "Password2", "1234567891");
        Patient patient3 = createPatient("Charlie", "Davis", "789 Pine Road", "charlie@example.com", "Password3", "1234567892");
        Patient patient4 = createPatient("Diana", "Brown", "101 Elm Boulevard", "diana@example.com", "Password4", "1234567893");
        Patient patient5 = createPatient("Ethan", "Miller", "202 Cedar Lane", "ethan@example.com", "Password5", "1234567894");
        Patient patient6 = createPatient("Fiona", "Wilson", "303 Maple Drive", "fiona@example.com", "Password6", "1234567895");

        patientRepository.save(patient1);
        patientRepository.save(patient2);
        patientRepository.save(patient3);
        patientRepository.save(patient4);
        patientRepository.save(patient5);
        patientRepository.save(patient6);
    }

    private Patient createPatient(String firstname, String lastname, String address, String email, String password, String phone) {
        Patient patient = new Patient();
        patient.setFirstname(firstname);
        patient.setLastname(lastname);
        patient.setAddress(address);
        patient.setEmail(email);
        patient.setPassword(passwordEncoder.encode(password));
        patient.setPhone(phone);
        patient.setUserRole(UserRole.PATIENT);
        return patient;
    }
}