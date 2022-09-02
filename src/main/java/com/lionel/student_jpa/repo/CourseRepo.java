package com.lionel.student_jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lionel.student_jpa.model.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course,String> {
    
}
