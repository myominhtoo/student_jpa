package com.lionel.student_jpa.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lionel.student_jpa.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    
    @Query("SELECT * FROM users u WHERE u.email = ?1 ")
    Optional<User> findByEmail( String email );

    @Query("SELECT MAX(id) max FROM users ")
    String getMaxId();

}
