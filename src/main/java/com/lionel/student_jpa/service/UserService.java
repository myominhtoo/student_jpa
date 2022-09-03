package com.lionel.student_jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lionel.student_jpa.model.User;
import com.lionel.student_jpa.repo.UserRepo;

@Service
public class UserService {
    
    @Autowired
    UserRepo userRepo;

    public boolean save( User user ){
        boolean isSaved = false;

        if( userRepo.save( user ) != null ){
            isSaved = true;
        }

        return isSaved;
    }

    public User findById( String userId ){
        return userRepo.findById( userId ).get();
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public boolean deleteOne( String userId ){
        boolean isDeleted = false;

       try{
        userRepo.deleteById( userId );
        isDeleted = true;
       }catch( Exception e ){
        isDeleted = false;
       }

       return isDeleted;
    }

    public boolean updateOne( User user ){
        boolean isUpdated = false;
        User savedUser = userRepo.findById(user.getId()).get();

        savedUser.setName( user.getName());
        savedUser.setEmail( user.getEmail());
        savedUser.setPassword( user.getPassword());
        savedUser.setRole( user.getRole());

        if( userRepo.save( savedUser ) != null ){
            isUpdated = true;
        }
        
        return isUpdated;
    }

    public boolean isEmailDuplicate( String email ){
        boolean  isDuplicate = false;

        if( userRepo.findByEmail( email ).isPresent() ){
            isDuplicate = true;
        }

        return  isDuplicate;
    }



}
