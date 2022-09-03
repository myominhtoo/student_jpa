package com.lionel.student_jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lionel.student_jpa.model.Student;
import com.lionel.student_jpa.repo.StudentRepo;

@Service
public class StudentService {
    
    @Autowired
    StudentRepo studentRepo;

    public boolean save( Student student ){
        boolean isSaved = false;

        if( studentRepo.save( student ) != null ){
            isSaved = true;
        }

        return isSaved;
    }

    public List<Student> findAll(){
        return studentRepo.findAll();
    }

    public Student findById( String studentId ){
        return studentRepo.findById( studentId ).get();
    }

    public boolean deleteOne( String studentId ){
        boolean isDeleted = false;

        try{
            studentRepo.deleteById( studentId );
            isDeleted = true;
        }catch( Exception e  ){
            isDeleted = false;
        }

        return isDeleted;
    }

    public boolean updateOne( Student student ){
        boolean isUpdated = false;
        Student savedStudent = studentRepo.findById( student.getId() ).get();

        savedStudent.setName( student.getName() );
        savedStudent.setDob( student.getDob() );
        savedStudent.setGender( student.getGender() );
        savedStudent.setEducation( student.getEducation() );
        savedStudent.setAttendCourses( student.getAttendCourses() );

        if( studentRepo.save( savedStudent ) != null ){
            isUpdated = true;
        }

        return isUpdated;    
    }

    public String getMaxId(){
        String maxId = studentRepo.getMaxId();
        return maxId == null ? "STU000" : maxId;
    }

}
