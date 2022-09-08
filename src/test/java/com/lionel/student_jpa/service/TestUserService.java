package com.lionel.student_jpa.service;

import com.lionel.student_jpa.model.User;
import com.lionel.student_jpa.repo.UserRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestUserService {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    private static User user;

    private static  List<User> users;

    @BeforeAll
    public static void doBeforeAllTests(){
        User user1 = new User();
        user1.setId("USR001");
        user1.setName("use1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("12345");

        User user2 = new User();
        user2.setId("USR002");
        user2.setName("user2");
        user2.setEmail("user2@gmail.com");
        user1.setPassword("12345");

        user = user1;

        users = new ArrayList<>();
        Collections.addAll( users , user1 , user2 );
    }

    @Test
    public void saveTest(){
        System.out.println(user);
        when( this.userRepo.save(user) ).thenReturn( user );
        this.userService.save( user );
        verify( userRepo , times(1)).save( user );
    }

    @Test
    public void findByIdTest(){
        Optional<User> savedUser = Optional.of( user );
        when( this.userRepo.findById("USR001")).thenReturn( savedUser );

        assertTrue( (this.userService.findById("USR001").getId().equals(savedUser.get().getId())) );
        verify( this.userRepo , times(1) ).findById("USR001");
    }

    @Test
    public void findAllTest(){
        when( this.userRepo.findAll() ).thenReturn( users );
        assertTrue( this.userService.findAll().size() == users.size() );
        verify( this.userRepo , times(1) ).findAll();
    }

    @Test
    public void deleteOneTest(){
        assertTrue( this.userService.deleteOne("USR001"));
        verify( this.userRepo , times(1)).deleteById("USR001");
    }

    @Test
    public void updateOneTest(){
        User updateUser = user;
        updateUser.setName("update username");

        Optional<User> savedUser = Optional.of( user );
        when( this.userRepo.findById("USR001")).thenReturn( savedUser );

        when( userRepo.save(updateUser) ).thenReturn(  updateUser );
        assertTrue( this.userService.updateOne( updateUser ));
        verify( this.userRepo ,times(1)).findById("USR001");
        verify( this.userRepo , times(1)).save( updateUser );
    }

    @Test
    public void isEmailDuplicateTest(){
        Optional<User>  presentUser = Optional.of( user );
        Optional<User> emptyUser = Optional.empty();

        String presentEmail = "present@gmail.com";
        String emptyEmail = "empty@gmail.com";

        when( userRepo.findByEmail( presentEmail )).thenReturn( presentUser );
        assertTrue( this.userService.isEmailDuplicate( presentEmail ) );
        verify( this.userRepo , times(1)).findByEmail( presentEmail );

        when( userRepo.findByEmail( emptyEmail )).thenReturn( emptyUser );
        assertFalse( this.userService.isEmailDuplicate( emptyEmail ));

        verify( this.userRepo , times(1)).findByEmail( emptyEmail );
    }

    @Test
    public void getMaxIdTest(){
       when( this.userRepo.getMaxId() ).thenReturn( "MAX" );
       assertEquals( "MAX" , this.userService.getMaxid() );
       verify( this.userRepo , times(1)).getMaxId();
    }

    @Test
    public void findByEmailTest(){
        Optional<User> returnUser = Optional.of( user );

        when( this.userRepo.findByEmail("find@gmail.com")).thenReturn( returnUser );
        assertTrue( user.getEmail().equals(this.userService.findByEmail("find@gmail.com").getEmail()));

        verify( this.userRepo , times(1) ).findByEmail("find@gmail.com") ;
    }

    @Test
    public void findWithIdTest(){

        when( this.userRepo.findWithId("USR001")).thenReturn( users );
        assertEquals( users.size() , this.userService.findWithId("USR001").size() );

        verify( this.userRepo , times(1)).findWithId("USR001");
    }

    @Test
    public void findWithNameTest(){
        when( this.userRepo.findWithName("name")).thenReturn( users );
        assertEquals( users.size() , this.userService.findWithName("name").size() );

        verify( this.userRepo , times(1)).findWithName("name");
    }

    @Test
    public void findWithIdAndNameTest(){
        when( this.userRepo.findWithIdAndName( "id" , "name")).thenReturn( users );
        assertEquals( users.size() , this.userService.findWithIdAndName( "id","name").size() );

        verify( this.userRepo , times(1)).findWithIdAndName( "id" , "name");
    }

}
