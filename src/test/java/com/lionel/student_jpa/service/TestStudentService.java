package com.lionel.student_jpa.service;

import com.lionel.student_jpa.model.Student;
import com.lionel.student_jpa.repo.StudentRepo;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TestStudentService {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepo studentRepo;

    @MockBean
    private StudentService studentService;

    private static Student student;

    private static List<Student> students;

    @BeforeAll
    public static void doBeforeAllTests(){

    }

}
