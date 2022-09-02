package com.lionel.student_jpa.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "courses" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course
{
    @Id
    private String id;
    private String name;    

    @ManyToMany(
        mappedBy = "attendCourses"
    )
    List<Student> students;
}
