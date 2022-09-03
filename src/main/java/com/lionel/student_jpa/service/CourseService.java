package com.lionel.student_jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lionel.student_jpa.model.Course;
import com.lionel.student_jpa.repo.CourseRepo;

@Service
public class CourseService
{
    
    @Autowired
    CourseRepo courseRepo;

    public boolean save( Course course ){

        boolean isSaved = false;

        if( courseRepo.save( course ) != null ){
            isSaved = true;
        }

        return isSaved;

    }

    public boolean isDuplicate( String courseId ){

        boolean isDuplicate = false;

        if( courseRepo.findById( courseId ).isPresent() ){
            isDuplicate = true;
        }

        return isDuplicate;
    }

    public List<Course> findAll(){
        return courseRepo.findAll();
    }

    public Course findById( String courseId ){
        return courseRepo.findById( courseId ).get();
    }

    
}
