//package kth.numi.userservice.service.Patient;
//
//import kth.numi.userservice.dto.UserDto;
//import kth.numi.userservice.model.Patient;
//import kth.numi.userservice.repository.PatientRepository;
//import kth.numi.userservice.roles.Role;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import static kth.numi.userservice.dto.UserDto.convertToDto;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@WebMvcTest(PatientServiceImpl.class)
//class PatientServiceImplTest {
//    @Autowired private PatientServiceImpl patientServiceImpl;
//    @MockBean PatientRepository patientRepository;
//    @MockBean PasswordEncoder passwordEncoder;
////    @MockBean JwtService jwtService;
//
//    @BeforeEach
//    void setUp() {
//        this.passwordEncoder = new BCryptPasswordEncoder();
//        patientServiceImpl = new PatientServiceImpl(patientRepository, passwordEncoder);
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    void testToGetPatientThatExists() {
//        Patient mockPatient = createPatient();
//
//        when(patientRepository.findById(1)).thenReturn(Optional.of(mockPatient));
//
//        ResponseEntity<?> response = patientServiceImpl.getPatient(1);
//
//        UserDto resultPatient = (UserDto) response.getBody();
//
//        assertNotNull(response, "Result should not be null if patient is found");
//
//        assertEquals(response.getBody(), convertToDto(mockPatient));
//        assertEquals(resultPatient.getId(), mockPatient.getId());
//        assertEquals(resultPatient.getRole(), mockPatient.getRole());
//        assertEquals(resultPatient.getPhone(), mockPatient.getPhone());
//        assertEquals(resultPatient.getAddress(), mockPatient.getAddress());
//        assertEquals(resultPatient.getFullname(), mockPatient.getFirstname() + " " + mockPatient.getLastname());
//        assertEquals(resultPatient.getEmail(), mockPatient.getEmail());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void testToGetPatientThatDontExist() {
//        when(patientRepository.findById(1)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = patientServiceImpl.getPatient(1);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Could not find this patient", response.getBody());
//    }
//
//    @Test
//    void testToGetPatientThatThrowsException() {
//        when(patientRepository.findById(1)).thenThrow(new RuntimeException("Simulated Internal Server Error"));
//
//        ResponseEntity<?> response = patientServiceImpl.getPatient(1);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertEquals("An error occurred", response.getBody());
//    }
//
//    @Test
//    void testToGetAllPatientsIfExist() {
//        Patient mockPatient1 = createPatient();
//        Patient mockPatient2 = createPatient();
//
//        List<Patient> mockPatientList = new ArrayList<>();
//        mockPatientList.add(mockPatient1);
//        mockPatientList.add(mockPatient2);
//
//        when(patientRepository.findAll()).thenReturn(mockPatientList);
//
//        ResponseEntity<?> response = patientServiceImpl.getAllPatients();
//
//        UserDto resultPatient1 = (UserDto) ((List) response.getBody()).get(0);
//        UserDto resultPatient2 = (UserDto) ((List) response.getBody()).get(1);
//
//        assertNotNull(response, "Result should not be null if patients is found");
//        assertEquals(resultPatient1, convertToDto(mockPatient1));
//        assertEquals(resultPatient2, convertToDto(mockPatient2));
//    }
//
//    @Test
//    void testToGetAllPatientsIfNotExist() {
//        when(patientRepository.findAll()).thenReturn(List.of());
//
//        ResponseEntity<?> response = patientServiceImpl.getAllPatients();
//
//        assertEquals(response.getBody(), "No patients found");
//        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    void testToGetAllPatientsIfItThrowsException() {
//        when(patientRepository.findAll()).thenThrow(new RuntimeException("Internal Server Error"));
//
//        ResponseEntity<?> response = patientServiceImpl.getAllPatients();
//
//        assertEquals(response.getBody(), "An error occurred");
//        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @Test
//    void testToSavePatientIfValid() {
//        Patient mockPatient = createPatient();
//
//        when(patientRepository.save(any(Patient.class))).thenReturn(mockPatient);
//
//        ResponseEntity<?> response = patientServiceImpl.savePatient(mockPatient);
//
//        assertEquals(response.getBody(), convertToDto(mockPatient));
//        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
//
//        verify(patientRepository, times(1)).save(any(Patient.class));
//    }
//
//    @Test
//    void testToSavePatientIfException() {
//        Patient mockPatient = createPatient();
//
//        when(patientRepository.save(any(Patient.class))).thenThrow(new RuntimeException("Internal Server Error"));
//
//        ResponseEntity<?> response = patientServiceImpl.savePatient(mockPatient);
//
//        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
//        assertEquals(response.getBody(), "Could not create this patient");
//    }
//
//    private Patient createPatient() {
//        Patient patient = new Patient();
//        patient.setId(1);
//        patient.setFirstname("Test");
//        patient.setLastname("Test");
//        patient.setAddress("Linköping");
//        patient.setEmail("test@test.com");
//        patient.setPassword(passwordEncoder.encode("PASSWORD123"));
//        patient.setPhone("0769350212");
//        patient.setRole(Role.PATIENT);
//        return patient;
//    }
//}