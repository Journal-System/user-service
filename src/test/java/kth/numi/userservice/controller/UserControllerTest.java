package kth.numi.userservice.controller;

import kth.numi.userservice.model.Doctor;
import kth.numi.userservice.model.User;
import kth.numi.userservice.roles.Role;
import kth.numi.userservice.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired private MockMvc mvc;
    @MockBean private UserService userService;
    @Test
    void testToGetUserWithValidId() throws Exception {
        User mockUser = createUser();

        given(userService.getUser(1))
                .willAnswer(invocation -> ResponseEntity.ok(mockUser));

        mvc.perform(get("/user/get/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("firstname").value("Test"))
                .andExpect(jsonPath("lastname").value("Test"))
                .andExpect(jsonPath("email").value("test@test.com"))
                .andExpect(jsonPath("password").value("PASSWORD123"))
                .andExpect(jsonPath("address").value("Stockholm"))
                .andExpect(jsonPath("phone").value("0769310491"))
                .andExpect(jsonPath("role").value("DOCTOR"));

        verify(userService, times(1)).getUser(1);
    }
    @Test
    void testToGetUserWithInvalidId() throws Exception {
        given(userService.getUser(1))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find this doctor"));

        mvc.perform(get("/user/get/{id}", 1)
                        .contentType(MediaType.TEXT_PLAIN)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.TEXT_PLAIN + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find this doctor"))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"));

        verify(userService, times(1)).getUser(1);
    }
    @Test
    void testToGetAllUsers() throws Exception {

        User mockUser1 = createUser();
        User mockUser2 = createUser2();
        List mockUserList = new ArrayList();
        mockUserList.add(mockUser1);
        mockUserList.add(mockUser2);

        given(userService.getAllUsers())
                .willAnswer(invocation -> ResponseEntity.ok(mockUserList));

        mvc.perform(get("/user/getAllUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].address").value("Stockholm"))
                .andExpect(jsonPath("$[0].email").value("test@test.com"))
                .andExpect(jsonPath("$[0].firstname").value("Test"))
                .andExpect(jsonPath("$[0].lastname").value("Test"))
                .andExpect(jsonPath("$[0].password").value("PASSWORD123"))
                .andExpect(jsonPath("$[0].phone").value("0769310491"))
                .andExpect(jsonPath("$[0].role").value("DOCTOR"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].address").value("Göteborg"))
                .andExpect(jsonPath("$[1].email").value("test2@test2.com"))
                .andExpect(jsonPath("$[1].firstname").value("Test2"))
                .andExpect(jsonPath("$[1].lastname").value("Test2"))
                .andExpect(jsonPath("$[1].password").value("PASSWORD123"))
                .andExpect(jsonPath("$[1].phone").value("04182471521"))
                .andExpect(jsonPath("$[1].role").value("PATIENT"));

        verify(userService, times(1)).getAllUsers();
    }
    private User createUser() {
        User user = new User();
        user.setId(1);
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setEmail("test@test.com");
        user.setPassword("PASSWORD123");
        user.setAddress("Stockholm");
        user.setPhone("0769310491");
        user.setRole(Role.DOCTOR);
        return user;
    }
    private Doctor createUser2() {
        Doctor user = new Doctor();
        user.setId(2);
        user.setFirstname("Test2");
        user.setLastname("Test2");
        user.setEmail("test2@test2.com");
        user.setPassword("PASSWORD123");
        user.setAddress("Göteborg");
        user.setPhone("04182471521");
        user.setRole(Role.PATIENT);
        return user;
    }
}