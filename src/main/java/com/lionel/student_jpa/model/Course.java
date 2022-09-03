package com.lionel.student_jpa.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table( name = "courses" )
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course
{
    @Id
    @NotEmpty( message = "Course Id must not be empty!" )
    private String id;

    @NotEmpty( message = "Course Name must not be empty!")
    private String name;    
}
