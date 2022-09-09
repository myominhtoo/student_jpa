package com.lionel.student_jpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lionel.student_jpa.model.Course;
import com.lionel.student_jpa.repo.CourseRepo;

@Service
public class CourseService {

    @Autowired
    CourseRepo courseRepo;

    public boolean save(Course course) {

        boolean isSaved = false;

        if (courseRepo.save(course) != null) {
            isSaved = true;
        }

        return isSaved;

    }

    public boolean isDuplicate(String courseId) {

        boolean isDuplicate = false;

        if (courseRepo.findById(courseId).isPresent()) {
            isDuplicate = true;
        }

        return isDuplicate;
    }

    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    public Course findById(String courseId) {
        Optional<Course> foundCourse = courseRepo.findById(courseId);
        return foundCourse.isPresent() ? foundCourse.get() : new Course();
    }

    public boolean deleteOne( String courseId ) {
        boolean isDeleted = false;

        try{
            courseRepo.deleteById(courseId);
            isDeleted = true;
        }catch( Exception e ){
            isDeleted = false;
        }

        return isDeleted;
    }

    public boolean updateOne( Course course ){
        boolean isUpdated = false;

        Optional<Course> foundCourse = courseRepo.findById( course.getId() );

        if( foundCourse.isEmpty() ){
            return isUpdated;
        }

        Course savedCourse = foundCourse.get();

        savedCourse.setName( course.getName() );

        if( courseRepo.save(savedCourse) != null ){
            isUpdated = true;
        }

        return isUpdated;
    }
}
