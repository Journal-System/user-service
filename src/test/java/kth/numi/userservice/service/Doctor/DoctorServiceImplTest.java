package kth.numi.userservice.service.Doctor;

import kth.numi.userservice.dto.UserDto;
import kth.numi.userservice.model.Doctor;
import kth.numi.userservice.repository.DoctorRepository;
import kth.numi.userservice.roles.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static kth.numi.userservice.dto.UserDto.convertToDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(DoctorServiceImpl.class)
class DoctorServiceImplTest {
    @Autowired private DoctorServiceImpl doctorServiceImpl;
    @MockBean DoctorRepository doctorRepository;
    @MockBean PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        doctorServiceImpl = new DoctorServiceImpl(doctorRepository, passwordEncoder);
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testToGetDoctorThatExists() {
        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1);
        mockDoctor.setFirstname("Test");
        mockDoctor.setLastname("Test");
        mockDoctor.setAddress("Stockholm");
        mockDoctor.setEmail("test@test.com");
        mockDoctor.setPassword(passwordEncoder.encode("PASSWORD123"));
        mockDoctor.setPhone("0769120231");
        mockDoctor.setRole(Role.DOCTOR);

        when(doctorRepository.findById(1)).thenReturn(Optional.of(mockDoctor));
        ResponseEntity<?> response = doctorServiceImpl.getDoctor(1);
        UserDto resultDoctor = (UserDto) response.getBody();
        assertNotNull(response, "Result should not be null if doctor is found");
        assertEquals(response.getBody(), convertToDto(mockDoctor));
        assertEquals(resultDoctor.getId(), mockDoctor.getId());
        assertEquals(resultDoctor.getRole(), mockDoctor.getRole());
        assertEquals(resultDoctor.getPhone(), mockDoctor.getPhone());
        assertEquals(resultDoctor.getAddress(), mockDoctor.getAddress());
        assertEquals(resultDoctor.getFullname(), mockDoctor.getFirstname() + " " + mockDoctor.getLastname());
        assertEquals(resultDoctor.getEmail(), mockDoctor.getEmail());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testToGetDoctorThatDontExist() {
        when(doctorRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = doctorServiceImpl.getDoctor(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Could not find this doctor", response.getBody());
    }

    @Test
    void testToGetDoctorThatThrowsException() {
        when(doctorRepository.findById(1)).thenThrow(new RuntimeException("Simulated Internal Server Error"));

        ResponseEntity<?> response = doctorServiceImpl.getDoctor(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(response.getBody(), "An error occurred");
    }

    @Test
    void testToGetAllDoctorsIfExist() {
        Doctor mockDoctor1 = new Doctor();
        mockDoctor1.setId(1);
        mockDoctor1.setFirstname("Test1");
        mockDoctor1.setLastname("Test1");
        mockDoctor1.setAddress("Stockholm");
        mockDoctor1.setEmail("test1@test1.com");
        mockDoctor1.setPassword(passwordEncoder.encode("PASSWORD123"));
        mockDoctor1.setPhone("0765931031");
        mockDoctor1.setRole(Role.DOCTOR);

        Doctor mockDoctor2 = new Doctor();
        mockDoctor2.setId(2);
        mockDoctor2.setFirstname("Test2");
        mockDoctor2.setLastname("Test2");
        mockDoctor2.setAddress("Göteborg");
        mockDoctor2.setEmail("test2@test2.com");
        mockDoctor2.setPassword(passwordEncoder.encode("PASSWORD123"));
        mockDoctor2.setPhone("0739130183");
        mockDoctor2.setRole(Role.DOCTOR);

        List<Doctor> mockDoctorList = new ArrayList<>();
        mockDoctorList.add(mockDoctor1);
        mockDoctorList.add(mockDoctor2);

        when(doctorRepository.findAll()).thenReturn(mockDoctorList);

        ResponseEntity<?> response = doctorServiceImpl.getAllDoctors();

        UserDto resultDoctor1 = (UserDto) ((List) response.getBody()).get(0);
        UserDto resultDoctor2 = (UserDto) ((List) response.getBody()).get(1);

        assertNotNull(response, "Result should not be null if doctors is found");
        assertEquals(resultDoctor1, convertToDto(mockDoctor1));
        assertEquals(resultDoctor2, convertToDto(mockDoctor2));
    }
    @Test
    void testToGetAllDoctorsIfNotExist() {
        when(doctorRepository.findAll()).thenReturn(List.of());

        ResponseEntity<?> response = doctorServiceImpl.getAllDoctors();

        assertEquals(response.getBody(), "No doctors found");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testToGetAllDoctorsIfItThrowsException() {
        when(doctorRepository.findAll()).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = doctorServiceImpl.getAllDoctors();

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testToSaveDoctorIfValid() {
        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1);
        mockDoctor.setFirstname("Test");
        mockDoctor.setLastname("Test");
        mockDoctor.setAddress("Malmö");
        mockDoctor.setEmail("test@test.com");
        mockDoctor.setPassword(passwordEncoder.encode("PASSWORD123"));
        mockDoctor.setPhone("076942013");
        mockDoctor.setRole(Role.DOCTOR);

        when(doctorRepository.save(any(Doctor.class))).thenReturn(mockDoctor);

        ResponseEntity<?> response = doctorServiceImpl.saveDoctor(mockDoctor);

        assertEquals(response.getBody(), convertToDto(mockDoctor));
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void testToSaveDoctorIfException() {
        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1);
        mockDoctor.setFirstname("Test");
        mockDoctor.setLastname("Test");
        mockDoctor.setAddress("Malmö");
        mockDoctor.setEmail("test@test.com");
        mockDoctor.setPassword(passwordEncoder.encode("PASSWORD123"));
        mockDoctor.setPhone("076942013");
        mockDoctor.setRole(Role.DOCTOR);

        when(doctorRepository.save(any(Doctor.class))).thenThrow(new RuntimeException("Internal Service Error"));

        ResponseEntity<?> response = doctorServiceImpl.saveDoctor(mockDoctor);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody(), "Could not create this doctor");
    }
}