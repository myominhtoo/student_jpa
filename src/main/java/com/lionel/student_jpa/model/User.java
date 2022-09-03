package com.lionel.student_jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

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

    @NotEmpty( message = "Name must not be empty!" )
    private String name;

    @NotEmpty( message = "Email must not be empty!")
    @Column( unique = true )
    private String email;

    @NotEmpty( message = "Password must not be empty!")
    private String password;

    @NotEmpty( message = "Role must not be empty!" )
    private String role;  
    
    @Transient
    private String confirmPassword;
}
