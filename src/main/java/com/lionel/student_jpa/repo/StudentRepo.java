package com.lionel.student_jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lionel.student_jpa.model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student,String>{
    
}