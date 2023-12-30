package kth.numi.userservice.service.Staff;

import kth.numi.userservice.dto.UserDto;
import kth.numi.userservice.model.Staff;
import kth.numi.userservice.repository.StaffRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static kth.numi.userservice.dto.UserDto.convertToDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(StaffServiceImpl.class)
class StaffServiceImplTest {

    @Autowired private StaffServiceImpl staffServiceImpl;
    @MockBean StaffRepository staffRepository;
    @MockBean PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        staffServiceImpl = new StaffServiceImpl(staffRepository, passwordEncoder);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToGetStaffThatExists() {
        Staff mockStaff = createStaff();

        when(staffRepository.findById(1)).thenReturn(Optional.of(mockStaff));

        ResponseEntity<?> response = staffServiceImpl.getStaff(1);

        UserDto resultStaff = (UserDto) response.getBody();

        assertNotNull(response, "Result should not be null if staff is found");

        assertEquals(response.getBody(), convertToDto(mockStaff));
        assertEquals(resultStaff.getId(), mockStaff.getId());
        assertEquals(resultStaff.getRole(), mockStaff.getRole());
        assertEquals(resultStaff.getPhone(), mockStaff.getPhone());
        assertEquals(resultStaff.getAddress(), mockStaff.getAddress());
        assertEquals(resultStaff.getFullname(), mockStaff.getFirstname() + " " + mockStaff.getLastname());
        assertEquals(resultStaff.getEmail(), mockStaff.getEmail());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testToGetStaffThatDontExist() {
        when(staffRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = staffServiceImpl.getStaff(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Could not find this staff", response.getBody());
    }

    @Test
    void testToGetStaffThatThrowsException() {
        when(staffRepository.findById(1)).thenThrow(new RuntimeException("Simulated Internal Server Error"));

        ResponseEntity<?> response = staffServiceImpl.getStaff(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred", response.getBody());
    }

    @Test
    void testToGetAllStaffsIfExist() {
        Staff mockStaff1 = createStaff();
        Staff mockStaff2 = createStaff();

        List<Staff> mockStaffList = new ArrayList<>();
        mockStaffList.add(mockStaff1);
        mockStaffList.add(mockStaff2);

        when(staffRepository.findAll()).thenReturn(mockStaffList);

        ResponseEntity<?> response = staffServiceImpl.getAllStaffs();

        UserDto resultStaff1 = (UserDto) ((List) response.getBody()).get(0);
        UserDto resultStaff2 = (UserDto) ((List) response.getBody()).get(1);

        assertNotNull(response, "Result should not be null if staffs is found");
        assertEquals(resultStaff1, convertToDto(mockStaff1));
        assertEquals(resultStaff2, convertToDto(mockStaff2));
    }

    @Test
    void testToGetAllStaffsIfNotExist() {
        when(staffRepository.findAll()).thenReturn(List.of());

        ResponseEntity<?> response = staffServiceImpl.getAllStaffs();

        assertEquals(response.getBody(), "No staffs found");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testToGetAllStaffsIfItThrowsException() {
        when(staffRepository.findAll()).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = staffServiceImpl.getAllStaffs();

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testToSaveStaffIfValid() {
        Staff mockStaff = createStaff();

        when(staffRepository.save(any(Staff.class))).thenReturn(mockStaff);

        ResponseEntity<?> response = staffServiceImpl.saveStaff(mockStaff);

        assertEquals(response.getBody(), convertToDto(mockStaff));
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        verify(staffRepository, times(1)).save(any(Staff.class));
    }

    @Test
    void testToSaveStaffIfException() {
        Staff mockStaff = createStaff();

        when(staffRepository.save(any(Staff.class))).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = staffServiceImpl.saveStaff(mockStaff);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody(), "Could not create this staff");
    }

    private Staff createStaff() {
        Staff staff = new Staff();
        staff.setId(1);
        staff.setFirstname("Test");
        staff.setLastname("Test");
        staff.setAddress("Jönköping");
        staff.setEmail("test@test.com");
        staff.setPassword(passwordEncoder.encode("PASSWORD123"));
        staff.setPhone("0769350212");
        staff.setRole(Role.STAFF);
        return staff;
    }
}