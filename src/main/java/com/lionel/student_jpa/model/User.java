package com.lionel.student_jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "users" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User 
{
    @Id
    private String id;
    private String name;
    private String password;
    private String role;    
}
