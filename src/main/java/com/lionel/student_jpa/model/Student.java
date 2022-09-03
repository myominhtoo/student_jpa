package com.lionel.student_jpa.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table( name = "students" )
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student 
{
    @Id
    @NotEmpty( message = "Student Id must not empty!")
    private String id;

    @NotEmpty( message = "Name must not be empty!" )
    private String name;

    @NotEmpty( message = "Phone must not be empty!")
    private String phone;

    @NotEmpty( message = "Gender must not be empty!")
    private String gender;

    @NotEmpty( message = "DOB must not be empty!" )
    private String dob;

    @NotEmpty( message = "Eduction must not be empty!")
    private String education;

    @ManyToMany
    @JoinTable(
        name = "students_attend_courses",
        joinColumns = @JoinColumn( name = "student_id" ),
        inverseJoinColumns = @JoinColumn( name = "course_id" )
    )
    List<Course> attendCourses;

    @Transient
    private String course;
}
