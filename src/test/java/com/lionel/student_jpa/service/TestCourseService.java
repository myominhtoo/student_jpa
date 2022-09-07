package com.lionel.student_jpa.service;

import com.lionel.student_jpa.model.Course;
import com.lionel.student_jpa.repo.CourseRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestCourseService {
    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private CourseService courseService;

    //test for save in bookService
    @Test
    public void saveTest(){
        Course course = new Course();
        course.setId("C001");
        course.setName("java");

        courseService.save( course );
        verify( courseRepo , times(1)).save( course );
    }

    @Test
    public void isDuplicateTest(){
        Course course = new Course();
        course.setId("C001");
        course.setName("java");

        Optional<Course> savedCourse = Optional.of(course);

        when( courseRepo.findById("C001") ).thenReturn( savedCourse );
        assertEquals( courseService.isDuplicate("C001") , true );
        assertEquals( courseService.isDuplicate("C002") , false );
    }

    @Test
    public void findAllTest(){
        List<Course> courses = new ArrayList<>();

        Course c1 = new Course();
        c1.setId("C001");
        c1.setName("java");

        Course c2 = new Course();
        c2.setId("C002");
        c2.setName("php");

        when( courseRepo.findAll() ).thenReturn( courses );

        List<Course> returnCourses = courseService.findAll();

        assertTrue( ( courses.size()  == returnCourses.size()));
    }

    @Test
    public void findByIdTest(){
        Course course = new Course();
        course.setId("C001");
        course.setName("java");

        Optional<Course> savedCourse = Optional.of( course );

        when( courseRepo.findById("C001")).thenReturn( savedCourse );

        Course foundCourse = courseService.findById("C001");
        assertTrue( course.getId().equals(foundCourse.getId()));
    }

}
