package com.example.studentcrud;

import com.example.studentcrud.dto.StudentResponse;
import com.example.studentcrud.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentcrudApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Check that the Spring Boot application starts successfully.
    @Test
    void contextLoads() {
    }

    // Check that creating a student returns HTTP 201 Created.
    @Test
    void createStudentReturns201() throws Exception {
        Student student = new Student();
        student.setName("Sobia Khan");
        student.setEmail("sobia@example.com");
        student.setAge(22);
        student.setCourse("MSc Computer Science");

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sobia Khan"))
                .andExpect(jsonPath("$.email").value("sobia@example.com"));
    }

    // Check that retrieving all students returns HTTP 200 OK.
    @Test
    void getAllStudentsReturns200() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk());
    }

    // Check that requesting a student that does not exist returns HTTP 404.
    @Test
    void getMissingStudentReturns404() throws Exception {
        mockMvc.perform(get("/students/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("Student not found with id: 999999"));
    }

    // Check that invalid student details return HTTP 400 Bad Request.
    @Test
    void invalidStudentReturns400() throws Exception {
        String invalidStudent = """
                {
                    "name": "",
                    "email": "not-an-email",
                    "age": -2,
                    "course": ""
                }
                """;

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidStudent))
                .andExpect(status().isBadRequest());
    }

    // Check that updating a student returns HTTP 200 OK.
    @Test
    void updateStudentReturns200() throws Exception {
        Student student = new Student();
        student.setName("Sobia Khan");
        student.setEmail("sobia@example.com");
        student.setAge(22);
        student.setCourse("MSc Computer Science");

        String createResponse = mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        StudentResponse createdStudent =
                objectMapper.readValue(createResponse, StudentResponse.class);

        Student updatedStudent = new Student();
        updatedStudent.setName("Sobia Khan");
        updatedStudent.setEmail("sobia@example.com");
        updatedStudent.setAge(23);
        updatedStudent.setCourse("Artificial Intelligence");

        mockMvc.perform(put("/students/" + createdStudent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.course").value("Artificial Intelligence"));
    }

    // Check that deleting a student returns HTTP 204 No Content.
    @Test
    void deleteStudentReturns204() throws Exception {
        Student student = new Student();
        student.setName("Test Student");
        student.setEmail("teststudent@example.com");
        student.setAge(21);
        student.setCourse("Computer Science");

        String createResponse = mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        StudentResponse createdStudent =
                objectMapper.readValue(createResponse, StudentResponse.class);

        Long studentId = createdStudent.getId();

        mockMvc.perform(delete("/students/" + studentId))
                .andExpect(status().isNoContent());

        // Confirm that the deleted student can no longer be found.
        mockMvc.perform(get("/students/" + studentId))
                .andExpect(status().isNotFound());
    }
}