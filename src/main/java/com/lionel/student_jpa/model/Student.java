package com.lionel.student_jpa.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "students" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student 
{
    @Id
    private String id;
    private String name;
    private String gender;
    private String dob;
    private String education;

    @ManyToMany
    @JoinTable(
        name = "students_attend_courses",
        joinColumns = @JoinColumn( name = "student_id" ),
        inverseJoinColumns = @JoinColumn( name = "course_id" )
    )
    List<Course> attendCourses;
}
