package com.lionel.student_jpa.service;

import java.util.List;
import java.util.Optional;

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
        Optional<Student> foundStu = studentRepo.findById( studentId );
        return foundStu.isPresent() ? foundStu.get() : new Student();
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

    public List<Student> findWithId( String id ){
        return studentRepo.findWithId(id);
    }

    public List<Student> findWithName( String name ){
        return studentRepo.findWithName( name );
    }

    public List<Student> findwithIdAndName( String id , String name ){
        return studentRepo.findWithIdAndName( id , name );
    }

    public List<Student> findWithCourse( String course ){
        return studentRepo.findWithCourse( course );
    }

    public List<Student> findWithNameAndCourse( String name , String course ){
        return studentRepo.findWithNameAndCourse(name, course);
    }

    public List<Student> findWithIdAndCourse( String id , String course ){
        return studentRepo.findWithIdAndCourse( id , course);    
    }

    public List<Student> findWithIdAndNameAndCourse( String id , String name , String course ){
        return studentRepo.findWithIdAndNameAndCourse(id, name, course);
    }
}
