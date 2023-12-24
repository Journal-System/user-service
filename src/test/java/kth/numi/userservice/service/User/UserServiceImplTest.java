//package kth.numi.userservice.service.User;
//
//import kth.numi.userservice.dto.UserDto;
//import kth.numi.userservice.model.User;
//import kth.numi.userservice.repository.UserRepository;
//import kth.numi.userservice.roles.Role;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import static kth.numi.userservice.dto.UserDto.convertToDto;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@WebMvcTest(UserServiceImpl.class)
//class UserServiceImplTest {
//    @Autowired private UserServiceImpl userServiceImpl;
//    @MockBean UserRepository userRepository;
//    @BeforeEach
//    void setUp() {
//        userServiceImpl = new UserServiceImpl(userRepository);
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testToGetUserThatExists() {
//        User mockUser = createUser();
//
//        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
//
//        ResponseEntity<?> response = userServiceImpl.getUser(1);
//
//        UserDto resultUser = (UserDto) response.getBody();
//
//        assertNotNull(response, "Result should not be null if user is found");
//
//        assertEquals(response.getBody(), convertToDto(mockUser));
//        assertEquals(resultUser.getId(), mockUser.getId());
//        assertEquals(resultUser.getRole(), mockUser.getRole());
//        assertEquals(resultUser.getPhone(), mockUser.getPhone());
//        assertEquals(resultUser.getAddress(), mockUser.getAddress());
//        assertEquals(resultUser.getFullname(), mockUser.getFirstname() + " " + mockUser.getLastname());
//        assertEquals(resultUser.getEmail(), mockUser.getEmail());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void testToGetUserThatDontExist() {
//        when(userRepository.findById(1)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = userServiceImpl.getUser(1);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Could not find this user", response.getBody());
//    }
//
//    @Test
//    void testToGetUserThatThrowsException() {
//        when(userRepository.findById(1)).thenThrow(new RuntimeException("Simulated Internal Server Error"));
//
//        ResponseEntity<?> response = userServiceImpl.getUser(1);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertEquals("An error occurred", response.getBody());
//    }
//
//    @Test
//    void testToGetAllUsersIfExist() {
//        User mockUser1 = createUser();
//        User mockUser2 = createUser();
//
//        List<User> mockUserList = new ArrayList<>();
//        mockUserList.add(mockUser1);
//        mockUserList.add(mockUser2);
//
//        when(userRepository.findAll()).thenReturn(mockUserList);
//
//        ResponseEntity<?> response = userServiceImpl.getAllUsers();
//
//        UserDto resultUser1 = (UserDto) ((List) response.getBody()).get(0);
//        UserDto resultUser2 = (UserDto) ((List) response.getBody()).get(1);
//
//        assertNotNull(response, "Result should not be null if users is found");
//        assertEquals(resultUser1, convertToDto(mockUser1));
//        assertEquals(resultUser2, convertToDto(mockUser2));
//    }
//
//    @Test
//    void testToGetAllUsersIfNotExist() {
//        when(userRepository.findAll()).thenReturn(List.of());
//
//        ResponseEntity<?> response = userServiceImpl.getAllUsers();
//
//        assertEquals(response.getBody(), "No users found");
//        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    void testToGetAllUsersIfItThrowsException() {
//        when(userRepository.findAll()).thenThrow(new RuntimeException("Internal Server Error"));
//
//        ResponseEntity<?> response = userServiceImpl.getAllUsers();
//
//        assertEquals(response.getBody(), "An error occurred");
//        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    private User createUser() {
//        User user = new User();
//        user.setId(1);
//        user.setFirstname("Test");
//        user.setLastname("Test");
//        user.setAddress("Linköping");
//        user.setEmail("test@test.com");
//        user.setPassword("PASSWORD123");
//        user.setPhone("0769350212");
//        user.setRole(Role.PATIENT);
//        return user;
//    }
//}