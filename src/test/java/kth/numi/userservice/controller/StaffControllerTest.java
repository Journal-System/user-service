package kth.numi.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kth.numi.userservice.model.Staff;
import kth.numi.userservice.roles.Role;
import kth.numi.userservice.service.Staff.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(StaffController.class)
class StaffControllerTest {

    @Autowired private MockMvc mvc;
    @MockBean private StaffService staffService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void testToGetStaffWithValidId() throws Exception {
        Staff mockStaff = createStaff();

        given(staffService.getStaff(1))
                .willAnswer(invocation -> ResponseEntity.ok(mockStaff));

        mvc.perform(get("/staff/get/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
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
                .andExpect(jsonPath("role").value("STAFF"));

        verify(staffService, times(1)).getStaff(1);
    }
    @Test
    void testToGetStaffWithInvalidId() throws Exception {
        given(staffService.getStaff(1))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find this staff"));

        mvc.perform(get("/staff/get/{id}", 1)
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(MediaType.TEXT_PLAIN + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find this staff"))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"));

        verify(staffService, times(1)).getStaff(1);
    }
    @Test
    void testToGetAllStaffs() throws Exception {

        Staff mockStaff1 = createStaff();
        Staff mockStaff2 = createStaff2();
        List mockStaffList = new ArrayList();
        mockStaffList.add(mockStaff1);
        mockStaffList.add(mockStaff2);

        given(staffService.getAllStaffs())
                .willAnswer(invocation -> ResponseEntity.ok(mockStaffList));

        mvc.perform(get("/staff/getAllStaffs")
                        .contentType(MediaType.APPLICATION_JSON)
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
                .andExpect(jsonPath("$[0].role").value("STAFF"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].address").value("Göteborg"))
                .andExpect(jsonPath("$[1].email").value("test2@test2.com"))
                .andExpect(jsonPath("$[1].firstname").value("Test2"))
                .andExpect(jsonPath("$[1].lastname").value("Test2"))
                .andExpect(jsonPath("$[1].password").value("PASSWORD123"))
                .andExpect(jsonPath("$[1].phone").value("04182471521"))
                .andExpect(jsonPath("$[1].role").value("STAFF"));

        verify(staffService, times(1)).getAllStaffs();
    }
    @Test
    void testToAddStaff() throws Exception {
        Staff mockStaff = createStaff();

        given(staffService.saveStaff(mockStaff))
                .willAnswer(invocation -> ResponseEntity.ok(mockStaff));

        mvc.perform(post("/staff/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockStaff)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockStaff)));

        verify(staffService, times(1)).saveStaff(mockStaff);
    }
    private Staff createStaff() {
        Staff staff = new Staff();
        staff.setId(1);
        staff.setFirstname("Test");
        staff.setLastname("Test");
        staff.setEmail("test@test.com");
        staff.setPassword("PASSWORD123");
        staff.setAddress("Stockholm");
        staff.setPhone("0769310491");
        staff.setRole(Role.STAFF);
        return staff;
    }
    private Staff createStaff2() {
        Staff staff = new Staff();
        staff.setId(2);
        staff.setFirstname("Test2");
        staff.setLastname("Test2");
        staff.setEmail("test2@test2.com");
        staff.setPassword("PASSWORD123");
        staff.setAddress("Göteborg");
        staff.setPhone("04182471521");
        staff.setRole(Role.STAFF);
        return staff;
    }
}