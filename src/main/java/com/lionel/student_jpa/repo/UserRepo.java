package com.lionel.student_jpa.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lionel.student_jpa.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    
    @Query( value = "SELECT * FROM users u WHERE u.email = ?1 " , nativeQuery = true)
    Optional<User> findByEmail( String email );

    @Query( value = "SELECT MAX(id) FROM users " , nativeQuery = true )
    String getMaxId();

    @Query( value = "SELECT * FROM users u WHERE u.id LIKE :id "  , nativeQuery = true )
    List<User> findWithId(@Param("id") String id );

    @Query( value = "SELECT * FROM users u WHERE u.name LIKE :name" , nativeQuery = true )
    List<User> findWithName(@Param("name") String name );

    @Query( value = "SELECT * FROM users u WHERE u.id LIKE :id AND u.name LIKE :name " , nativeQuery = true )
    List<User> findWithIdAndName( @Param("id") String id , @Param("name") String name );

}
