package com.lionel.student_jpa.controller;

import com.lionel.student_jpa.model.Course;
import com.lionel.student_jpa.model.Student;
import com.lionel.student_jpa.model.User;
import com.lionel.student_jpa.repo.StudentRepo;
import com.lionel.student_jpa.service.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class TestStudentController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepo studentRepo;

    @MockBean
    private StudentService studentService;


    private static Student student;

    private static User user;

    private static List<Student> students;

    private static List<Course> courses;

    @BeforeAll
    public static void doBeforeAll(){
        user = new User();
        user.setId("USR001");

        student = new Student();

        Student stu1 = new Student();
        stu1.setName("stu1");
        stu1.setId("STU001");

        Student stu2 = new Student();
        stu2.setName("stu2");
        stu2.setId("STU002");

        students = new ArrayList<>();
        Collections.addAll( students ,stu1 , stu2 );

        Course course1 = new Course();
        course1.setId("C001");
        course1.setName("java");

        Course course2 = new Course();
        course2.setId("C002");
        course2.setName("php");

        courses = new ArrayList<>();
        Collections.addAll( courses , course1 , course2 );
    }

    @Test
    public void getStudentsPage() throws Exception {
        this.mockMvc.perform( get("/students").sessionAttr("authUser" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("STU003") )
                .andExpect( model().attributeExists("students" , "student"));
    }

    @Test
    public void getStudentCreatePageTest() throws  Exception {

        when( this.studentService.getMaxId() ).thenReturn("STU00");

        this.mockMvc.perform( get("/students/new").sessionAttr("authUser",user) )
                .andExpect( status().isOk() )
                .andExpect( view().name("STU001") )
                .andExpect( model().attributeExists("courses","student"));
    }

    @Test
    public void postStudentCreateWithBindErrorTest() throws Exception {
        student.setId("STU001");
        student.setEducation("");
        student.setName("");
        student.setPhone("");
        student.setDob("");
        student.setGender("");

        this.mockMvc.perform( post("/students/new").flashAttr("student",student).sessionAttr("authUser" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("STU001"))
                .andExpect( model().attributeExists("student","courses"));

     }

     @Test
    public void postStudentCreateWithSaveErrorTest() throws Exception {
         student.setId("STU001");
         student.setEducation("edu");
         student.setName("name");
         student.setPhone("1234");
         student.setDob("2001-01-01");
         student.setGender("gender");

         when( this.studentService.save(student)).thenReturn( false );

         this.mockMvc.perform( post("/students/new").flashAttr("student",student).sessionAttr("authUser" , user ) )
                 .andExpect( status().isOk() )
                 .andExpect( view().name("STU001"))
                 .andExpect( model().attributeExists("error","courses","student" ));
     }

     @Test
     public void postStudentCreateWithOkTest() throws Exception {
         student.setId("STU001");
         student.setEducation("edu");
         student.setName("name");
         student.setPhone("1234");
         student.setDob("2001-01-01");
         student.setGender("gender");

         when( this.studentService.save(student)).thenReturn( true );

         this.mockMvc.perform( post("/students/new").flashAttr("student",student).sessionAttr("authUser" , user ) )
                 .andExpect( status().is(302) )
                 .andExpect( redirectedUrl("/students?msg=Successfully Registered!"));
     }

    @Test
    public void getStudentDetailPageWithErrorTest() throws Exception {
        when( this.studentService.findById("STU001")).thenReturn( null );

        this.mockMvc.perform( get("/students/{id}" , "STU001").sessionAttr("authUser",user) )
                .andExpect( status().is( 302 ) )
                .andExpect( redirectedUrl("/students?msg=Something went wrong!"));

    }

    @Test
    public void getStudentDetailPageWithOkTest() throws Exception {
        when( this.studentService.findById("STU001")).thenReturn( student );

        this.mockMvc.perform( get("/students/{id}" , "STU001").sessionAttr("authUser",user) )
                .andExpect( status().is( 200 ) )
                .andExpect( view().name("STU002"))
                .andExpect( model().attributeExists("courses","student"));
    }

    @Test
    public void getStudentDeleteWithErrorTest() throws Exception {
        when( this.studentService.deleteOne("STU001")).thenReturn( false );

        this.mockMvc.perform( get("/students/{id}/delete" , "STU001").sessionAttr("authUser" , user ) )
                .andExpect( status().is(302) )
                .andExpect( redirectedUrl("/students?error=Something went wrong!"));
    }

    @Test
    public  void getStudentDeleteWithOkTest() throws Exception {
        when( this.studentService.deleteOne("STU001")).thenReturn( true );

        this.mockMvc.perform( get("/students/{id}/delete" , "STU001").sessionAttr("authUser" , user ) )
                .andExpect( status().is(302) )
                .andExpect( redirectedUrl("/students?msg=Successfully Deleted!"));
    }

    @Test
    public void getStudentUpdatePageWithErrorTest() throws Exception {

        when( this.studentService.findById("STU001") ).thenReturn( null );

        this.mockMvc.perform( get("/students/{id}/update" , "STU001").sessionAttr("authUser" , user ) )
                .andExpect( status().is( 302 ) )
                .andExpect( redirectedUrl("/students?msg=Something went wrong!") );
    }

    @Test
    public void getStudentUpdatePageWithOkTest() throws  Exception {
        when( this.studentService.findById("STU001") ).thenReturn( student );

        this.mockMvc.perform( get("/students/{id}/update" , "STU001").sessionAttr("authUser" , user ) )
                .andExpect( status().is( 200 ) )
                .andExpect( view().name("STU002-01"))
                .andExpect( model().attributeExists("student","courses") );
    }

    @Test
    public void postStudentUpdateWithBindErrorTest() throws Exception {
        student.setId("STU001");
        student.setEducation("");
        student.setName("");
        student.setPhone("");
        student.setDob("");
        student.setGender("");

        this.mockMvc.perform( post("/students/{id}/update" , "STU001").flashAttr("student",student).sessionAttr("authUser", user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("STU002-01") )
                .andExpect( model().attributeExists("student","courses"));
    }

    @Test
    public void postStudentUpdateWithSaveErrorTest() throws  Exception {
        student.setId("STU001");
        student.setEducation("edu");
        student.setName("name");
        student.setPhone("123");
        student.setDob("2001-01-01");
        student.setGender("gender");

        when( this.studentService.updateOne(student) ).thenReturn( false );

        this.mockMvc.perform( post("/students/{id}/update" , "STU001").flashAttr("student",student).sessionAttr("authUser", user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("STU002-01") )
                .andExpect( model().attributeExists("student","courses" , "error"));
    }

    @Test
    public void postStudentUpdateWithOkTest() throws  Exception {
        student.setId("STU001");
        student.setEducation("edu");
        student.setName("name");
        student.setPhone("123");
        student.setDob("2001-01-01");
        student.setGender("gender");

        when( this.studentService.updateOne(student) ).thenReturn( true );

        this.mockMvc.perform( post("/students/{id}/update" , "STU001").flashAttr("student",student).sessionAttr("authUser", user ) )
                .andExpect( status().is(302))
                .andExpect( redirectedUrl("/students?msg=Successfully Updated!"));
    }
}
