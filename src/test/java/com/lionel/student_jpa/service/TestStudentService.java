package com.lionel.student_jpa.service;

import com.lionel.student_jpa.model.Student;
import com.lionel.student_jpa.repo.StudentRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestStudentService {

    @Mock
    private StudentRepo studentRepo;

    @InjectMocks
    private StudentService studentService;

    private static Student student;

    private static List<Student> students;

    @BeforeAll
    public static void doBeforeAllTests(){
        Student stu1 = new Student();
        stu1.setId("STU001");
        stu1.setName("stu1");
        stu1.setDob("2000-02-02");
        stu1.setPhone("0912345");
        stu1.setEducation("Master");

        Student stu2 = new Student();
        stu2.setId("STU002");
        stu2.setName("stu2");
        stu2.setDob("2000-02-02");
        stu2.setPhone("0912345");
        stu2.setEducation("Master");

        student = stu1;

        students = new ArrayList<>();
        Collections.addAll( students , stu1 , stu2 );
    }

    @Test
    public void saveTest(){
        when( this.studentRepo.save(student) ).thenReturn( student );
        this.studentService.save( student );
        verify( this.studentRepo , times(1)).save( student );
    }

    @Test
    public void findAllTest(){
        when( this.studentRepo.findAll() ).thenReturn( students );
        assertEquals( this.studentService.findAll().size() , students.size() );

        verify( this.studentRepo , times(1) ).findAll();
    }

    @Test
    public void findByIdTest(){
        Optional<Student> presentStudent = Optional.of( student );

        when( this.studentRepo.findById("STU001") ).thenReturn( presentStudent );
        assertTrue( this.studentService.findById("STU001").getId().equals(student.getId()) );
        verify( this.studentRepo , times(1)).findById("STU001");
    }

    @Test
    public void deleteOneTest(){
        this.studentService.deleteOne("STU001");
        verify( this.studentRepo , times(1)).deleteById("STU001");
    }

    @Test
    public void updateOneTest(){
        Optional<Student> savedStu = Optional.of( student );

        when( this.studentRepo.findById("STU001")).thenReturn( savedStu );
        Student newStu = this.studentService.findById("STU001");
        verify( this.studentRepo , times(1) ).findById("STU001");

        when( this.studentRepo.save( newStu )).thenReturn( newStu );
        this.studentService.save( newStu );
        verify( this.studentRepo , times(1 )).save( newStu );
    }

    @Test
    public void getMaxIdTest(){
        when( this.studentRepo.getMaxId() ).thenReturn( null );
        assertTrue( this.studentService.getMaxId().equals("STU000"));
        when( this.studentRepo.getMaxId() ).thenReturn( "USR001" );

        assertTrue( this.studentService.getMaxId().equals("USR001"));
        verify( this.studentRepo , times(2) ).getMaxId();
    }

    @Test
    public void findWithIdTest(){
        when( this.studentRepo.findWithId("STU001")).thenReturn( students );
        assertTrue( this.studentService.findWithId("STU001").size() == students.size() );
        verify( this.studentRepo , times(1)).findWithId("STU001");
    }

    @Test
    public void findWithNameTest(){
        when( this.studentRepo.findWithName("STU001")).thenReturn( students );
        assertTrue( this.studentService.findWithName("STU001").size() == students.size() );
        verify( this.studentRepo , times(1)).findWithName("STU001");
    }

    @Test
    public void findWithCourseTest(){
        when( this.studentRepo.findWithCourse("STU001")).thenReturn( students );
        assertTrue( this.studentService.findWithCourse("STU001").size() == students.size() );
        verify( this.studentRepo , times(1)).findWithCourse("STU001");
    }

    @Test
    public void findWithIdAndNameTest(){
        when( this.studentRepo.findWithIdAndName("STU001" , "name" )).thenReturn( students );
        assertTrue( this.studentService.findwithIdAndName("STU001" , "name").size() == students.size() );
        verify( this.studentRepo , times(1)).findWithIdAndName("STU001","name");
    }

    @Test
    public void findWithIdAndCourseTest(){
        when( this.studentRepo.findWithIdAndCourse("STU001" , "name" )).thenReturn( students );
        assertTrue( this.studentService.findWithIdAndCourse("STU001" , "name").size() == students.size() );
        verify( this.studentRepo , times(1)).findWithIdAndCourse("STU001","name");
    }

    @Test
    public void findWithNameAndCourseTest(){
        when( this.studentRepo.findWithNameAndCourse("STU001" , "name" )).thenReturn( students );
        assertTrue( this.studentService.findWithNameAndCourse("STU001" , "name").size() == students.size() );
        verify( this.studentRepo , times(1)).findWithNameAndCourse("STU001","name");
    }

    @Test
    public void findWithIdAndNameAndCourseTest(){
        when( this.studentRepo.findWithIdAndNameAndCourse("id" , "name" , "course")).thenReturn( students );
        assertTrue( this.studentService.findWithIdAndNameAndCourse("id","name","course").size() == students.size() );
        verify( this.studentRepo ,times(1)).findWithIdAndNameAndCourse("id","name","course");
    }

}
