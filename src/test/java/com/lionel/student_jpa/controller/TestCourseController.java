package com.lionel.student_jpa.controller;

import com.lionel.student_jpa.model.Course;
import com.lionel.student_jpa.model.User;
import com.lionel.student_jpa.repo.CourseRepo;
import com.lionel.student_jpa.service.CourseService;
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
public class TestCourseController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepo courseRepo;

    @MockBean
    private CourseService courseService;

    private static User user;

    private static Course course;

    private static List<Course> courses;

    private final String REDIRECT_URL = "/login?error=Please login to continue!";

    @BeforeAll
    public static void doBeforeTests(){
        user = new User();
        course = new Course();

        Course c1 = new Course();
        c1.setId("C001");
        c1.setName("java");

        Course c2 = new Course();
        c2.setId("C002");
        c2.setName("js");

        courses = new ArrayList<>();

        Collections.addAll( courses , c1 , c2 );
    }

    @Test
    public void getCourseCreatePageTest() throws Exception {
        this.mockMvc.perform( get("/courses/new").sessionAttr("authUser",user) )
                .andExpect( status().is(200))
                .andExpect(model().attributeExists("course"))
                .andExpect(view().name("BUD003"));
    }

    @Test
    public void postCreateCourseWithBindErrorTest() throws  Exception {
        course.setId("id");
        course.setName("");
        this.mockMvc.perform( post("/courses/new").flashAttr("course",course).sessionAttr( "authUser" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("BUD003"))
                .andExpect( model().attributeExists("course"));
    }

    @Test
    public void postCourseCreateWithDuplicateTest() throws  Exception {
        course.setId("id");
        course.setName("name");

        when( this.courseService.isDuplicate("id") ).thenReturn( true );

        this.mockMvc.perform( post("/courses/new").flashAttr("course",course).sessionAttr( "authUser" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("BUD003"))
                .andExpect( model().attributeExists("course","error"));
    }
    @Test
    public void postCourseCreateWithSaveErrorTest() throws Exception {
        course.setId("id");
        course.setName("name");

        when( this.courseService.save(course)).thenReturn( false );

        this.mockMvc.perform( post("/courses/new" ).flashAttr("course",course).sessionAttr("authUser" , user))
                .andExpect( status().isOk() )
                .andExpect( view().name("BUD003"))
                .andExpect( model().attributeExists("course","error"));
    }

    @Test
    public void postCourseCreateWithOkTest() throws  Exception {
        course.setId("id");
        course.setName("name");

        when( this.courseService.save(course)).thenReturn( true );

        this.mockMvc.perform( post("/courses/new" ).flashAttr("course",course).sessionAttr("authUser" , user))
                .andExpect( status().isOk() )
                .andExpect( view().name("BUD003"))
                .andExpect( model().attributeExists("course","msg"));
    }
}
