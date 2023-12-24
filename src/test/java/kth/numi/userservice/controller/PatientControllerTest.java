//package kth.numi.userservice.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kth.numi.userservice.model.Patient;
//import kth.numi.userservice.roles.Role;
//import kth.numi.userservice.service.Patient.PatientService;
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
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
//@WebMvcTest(PatientController.class)
//class PatientControllerTest {
//
//    @Autowired private MockMvc mvc;
//    @MockBean private PatientService patientService;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Test
//    void testToGetPatientWithValidId() throws Exception {
//        Patient mockPatient = createPatient();
//
//        given(patientService.getPatient(1))
//                .willAnswer(invocation -> ResponseEntity.ok(mockPatient));
//
//        mvc.perform(get("/patient/get/{id}", 1)
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
//                .andExpect(jsonPath("role").value("PATIENT"));
//
//        verify(patientService, times(1)).getPatient(1);
//    }
//    @Test
//    void testToGetPatientWithInvalidId() throws Exception {
//        given(patientService.getPatient(1))
//                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find this patient"));
//
//        mvc.perform(get("/patient/get/{id}", 1)
//                        .contentType(MediaType.TEXT_PLAIN)
//                        .accept(MediaType.TEXT_PLAIN + ";charset=UTF-8"))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Could not find this patient"))
//                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"));
//
//        verify(patientService, times(1)).getPatient(1);
//    }
//    @Test
//    void testToGetAllPatients() throws Exception {
//
//        Patient mockPatient1 = createPatient();
//        Patient mockPatient2 = createPatient2();
//        List mockPatientList = new ArrayList();
//        mockPatientList.add(mockPatient1);
//        mockPatientList.add(mockPatient2);
//
//        given(patientService.getAllPatients())
//                .willAnswer(invocation -> ResponseEntity.ok(mockPatientList));
//
//        mvc.perform(get("/patient/getAllPatients")
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
//                .andExpect(jsonPath("$[0].role").value("PATIENT"))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].address").value("Göteborg"))
//                .andExpect(jsonPath("$[1].email").value("test2@test2.com"))
//                .andExpect(jsonPath("$[1].firstname").value("Test2"))
//                .andExpect(jsonPath("$[1].lastname").value("Test2"))
//                .andExpect(jsonPath("$[1].password").value("PASSWORD123"))
//                .andExpect(jsonPath("$[1].phone").value("04182471521"))
//                .andExpect(jsonPath("$[1].role").value("PATIENT"));
//
//        verify(patientService, times(1)).getAllPatients();
//    }
//    @Test
//    void testToAddPatient() throws Exception {
//        Patient mockPatient = createPatient();
//
//        given(patientService.savePatient(mockPatient))
//                .willAnswer(invocation -> ResponseEntity.ok(mockPatient));
//
//        mvc.perform(post("/patient/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(mockPatient)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(mockPatient)));
//
//        verify(patientService, times(1)).savePatient(mockPatient);
//    }
//    private Patient createPatient() {
//        Patient patient = new Patient();
//        patient.setId(1);
//        patient.setFirstname("Test");
//        patient.setLastname("Test");
//        patient.setEmail("test@test.com");
//        patient.setPassword("PASSWORD123");
//        patient.setAddress("Stockholm");
//        patient.setPhone("0769310491");
//        patient.setRole(Role.PATIENT);
//        return patient;
//    }
//    private Patient createPatient2() {
//        Patient patient = new Patient();
//        patient.setId(2);
//        patient.setFirstname("Test2");
//        patient.setLastname("Test2");
//        patient.setEmail("test2@test2.com");
//        patient.setPassword("PASSWORD123");
//        patient.setAddress("Göteborg");
//        patient.setPhone("04182471521");
//        patient.setRole(Role.PATIENT);
//        return patient;
//    }
//}