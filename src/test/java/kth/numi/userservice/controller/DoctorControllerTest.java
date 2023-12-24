//package kth.numi.userservice.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kth.numi.userservice.model.Doctor;
//import kth.numi.userservice.roles.Role;
//import kth.numi.userservice.service.Doctor.DoctorService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import java.util.ArrayList;
//import java.util.List;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(DoctorController.class)
//class DoctorControllerTest {
//
//    @Autowired private MockMvc mvc;
//    @MockBean private DoctorService doctorService;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Test
//    void testToGetDoctorWithValidId() throws Exception {
//        Doctor mockDoctor = createDoctor();
//
//        given(doctorService.getDoctor(1))
//                .willAnswer(invocation -> ResponseEntity.ok(mockDoctor));
//
//        mvc.perform(get("/doctor/get/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").value("1"))
//                .andExpect(jsonPath("firstname").value("Test"))
//                .andExpect(jsonPath("lastname").value("Test"))
//                .andExpect(jsonPath("email").value("test@test.com"))
//                .andExpect(jsonPath("password").value("PASSWORD123"))
//                .andExpect(jsonPath("address").value("Stockholm"))
//                .andExpect(jsonPath("phone").value("0769310491"))
//                .andExpect(jsonPath("role").value("DOCTOR"));
//
//        verify(doctorService, times(1)).getDoctor(1);
//    }
//    @Test
//    void testToGetDoctorWithInvalidId() throws Exception {
//        given(doctorService.getDoctor(1))
//                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find this doctor"));
//
//        mvc.perform(get("/doctor/get/{id}", 1)
//                        .contentType(MediaType.TEXT_PLAIN)
//                        .accept(MediaType.TEXT_PLAIN + ";charset=UTF-8"))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Could not find this doctor"))
//                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"));
//
//        verify(doctorService, times(1)).getDoctor(1);
//    }
//    @Test
//    void testToGetAllDoctors() throws Exception {
//
//        Doctor mockDoctor1 = createDoctor();
//        Doctor mockDoctor2 = createDoctor2();
//        List mockDoctorList = new ArrayList();
//        mockDoctorList.add(mockDoctor1);
//        mockDoctorList.add(mockDoctor2);
//
//        given(doctorService.getAllDoctors())
//                .willAnswer(invocation -> ResponseEntity.ok(mockDoctorList));
//
//        mvc.perform(get("/doctor/getAllDoctors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].address").value("Stockholm"))
//                .andExpect(jsonPath("$[0].email").value("test@test.com"))
//                .andExpect(jsonPath("$[0].firstname").value("Test"))
//                .andExpect(jsonPath("$[0].lastname").value("Test"))
//                .andExpect(jsonPath("$[0].password").value("PASSWORD123"))
//                .andExpect(jsonPath("$[0].phone").value("0769310491"))
//                .andExpect(jsonPath("$[0].role").value("DOCTOR"))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].address").value("Göteborg"))
//                .andExpect(jsonPath("$[1].email").value("test2@test2.com"))
//                .andExpect(jsonPath("$[1].firstname").value("Test2"))
//                .andExpect(jsonPath("$[1].lastname").value("Test2"))
//                .andExpect(jsonPath("$[1].password").value("PASSWORD123"))
//                .andExpect(jsonPath("$[1].phone").value("04182471521"))
//                .andExpect(jsonPath("$[1].role").value("DOCTOR"));
//
//        verify(doctorService, times(1)).getAllDoctors();
//    }
//    @Test
//    void testToAddDoctor() throws Exception {
//        Doctor mockDoctor = createDoctor();
//
//        given(doctorService.saveDoctor(mockDoctor))
//                .willAnswer(invocation -> ResponseEntity.ok(mockDoctor));
//
//        mvc.perform(post("/doctor/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(mockDoctor)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(mockDoctor)));
//
//        verify(doctorService, times(1)).saveDoctor(mockDoctor);
//    }
//    private Doctor createDoctor() {
//        Doctor doctor = new Doctor();
//        doctor.setId(1);
//        doctor.setFirstname("Test");
//        doctor.setLastname("Test");
//        doctor.setEmail("test@test.com");
//        doctor.setPassword("PASSWORD123");
//        doctor.setAddress("Stockholm");
//        doctor.setPhone("0769310491");
//        doctor.setRole(Role.DOCTOR);
//        return doctor;
//    }
//    private Doctor createDoctor2() {
//        Doctor doctor = new Doctor();
//        doctor.setId(2);
//        doctor.setFirstname("Test2");
//        doctor.setLastname("Test2");
//        doctor.setEmail("test2@test2.com");
//        doctor.setPassword("PASSWORD123");
//        doctor.setAddress("Göteborg");
//        doctor.setPhone("04182471521");
//        doctor.setRole(Role.DOCTOR);
//        return doctor;
//    }
//
//}