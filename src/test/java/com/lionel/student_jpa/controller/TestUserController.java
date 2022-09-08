package com.lionel.student_jpa.controller;

import com.lionel.student_jpa.model.User;
import com.lionel.student_jpa.repo.UserRepo;
import com.lionel.student_jpa.service.UserService;
import lombok.ToString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private UserService userService;

    private static User user;

    private static List<User> users;

    @BeforeAll
    public static void doBeforeAll(){
        user = new User();

        User user1 = new User();
        user1.setId("1");
        user1.setName("name1");

        User user2 = new User();
        user2.setId("2");
        user2.setName("name2");
    }

    @Test
    public void getLoginPageTest() throws Exception {
        this.mockMvc.perform( get("/login") )
                .andExpect( view().name("LGN001"))
                .andExpect( model().attributeExists("user"));
    }

    @Test
    public void postLoginWithBlankTest() throws  Exception {
        user.setId("");
        user.setPassword("");

        this.mockMvc.perform( post("/login").flashAttr("user",user) )
                .andExpect( view().name("LGN001"))
                .andExpect( model().attributeExists("user" , "error"));
    }

    @Test
    public void postLoginWithInvalidDataTest() throws  Exception {
        user.setId("USR001");
        user.setPassword("test");

        when( this.userService.findById("USR001") ).thenReturn( null );

        this.mockMvc.perform( post("/login").flashAttr("user",user) )
                .andExpect( view().name("LGN001"))
                .andExpect( model().attributeExists("user" , "error"));
    }

    @Test
    public void postLoginWithOkTest() throws Exception {
        user.setId("USR001");
        user.setPassword("test");

        when( this.userService.findById("USR001") ).thenReturn( user );

        this.mockMvc.perform( post("/login").flashAttr("user",user) )
                .andExpect( status().is(302))
                .andExpect( redirectedUrl("/home"));
    }

    @Test
    public void getUsersPageTest() throws Exception {
        this.mockMvc.perform( get("/users").sessionAttr("authUser", user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR003"))
                .andExpect( model().attributeExists("users","user"));
    }

    @Test
    public void getUserCreatePageTest() throws  Exception {
        this.mockMvc.perform(get("/users/new").sessionAttr("authUser" , user ))
                .andExpect( status().isOk() )
                .andExpect( view().name("USR001"))
                .andExpect( model().attributeExists("user"));
    }

    @Test
    public void postCreateUserWithBindErrorTest() throws  Exception {
        user.setName("");
        user.setEmail("");
        user.setPassword("");
        user.setConfirmPassword("");
        user.setRole("");

        this.mockMvc.perform( post("/users/new").sessionAttr("authUser",user).flashAttr( "user" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR001"))
                .andExpect( model().attributeExists("user"));
    }

    @Test
    public void postCreateUserWithPasswordErrorTest() throws Exception {
        user.setName("name");
        user.setEmail("email");
        user.setPassword("123");
        user.setConfirmPassword("321");
        user.setRole("admin");

        this.mockMvc.perform( post("/users/new").sessionAttr("authUser",user).flashAttr( "user" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR001"))
                .andExpect( model().attributeExists("user" , "error"));

    }

    @Test
    public void postCreateUserWithDuplicateEmailErrorTest() throws  Exception {
        user.setName("name");
        user.setEmail("email");
        user.setPassword("123");
        user.setConfirmPassword("123");
        user.setRole("admin");

        when( this.userService.isEmailDuplicate("email")).thenReturn( true );

        this.mockMvc.perform( post("/users/new").sessionAttr("authUser",user).flashAttr( "user" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR001"))
                .andExpect( model().attributeExists("user" , "error"));

    }

    @Test
    public void postCreateUserWithSaveErrorTest() throws Exception {
        user.setName("name");
        user.setEmail("email");
        user.setPassword("123");
        user.setConfirmPassword("123");
        user.setRole("admin");

        when( this.userService.isEmailDuplicate("email")).thenReturn( false );
        when( this.userService.save( user )).thenReturn( false );
        when( this.userService.getMaxid() ).thenReturn("USR00");

        this.mockMvc.perform( post("/users/new").sessionAttr("authUser",user).flashAttr( "user" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR001"))
                .andExpect( model().attributeExists("user" , "error"));
    }

    @Test
    public void postCreateUserWithOkTest() throws  Exception {
        user.setName("name");
        user.setEmail("email");
        user.setPassword("123");
        user.setConfirmPassword("123");
        user.setRole("admin");

        when( this.userService.isEmailDuplicate("email")).thenReturn( false );
        when( this.userService.save( user )).thenReturn( true );
        when( this.userService.getMaxid() ).thenReturn("USR00");

        this.mockMvc.perform( post("/users/new").sessionAttr("authUser",user).flashAttr( "user" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR001"))
                .andExpect( model().attributeExists("user" , "msg"));
    }

    @Test
    public void getDeleteUserWithErrorTest() throws  Exception {
        when( this.userService.deleteOne("USR001")).thenReturn(false);

       this.mockMvc.perform( get("/users/{id}/delete" , "USR001").sessionAttr("authUser",user) )
               .andExpect( status().is( 200 ) )
               .andExpect( view().name("USR003"))
               .andExpect( model().attributeExists("users","user","error"));

    }


    @Test
    public void getDeleteUserWithOkTest() throws  Exception {
        when( this.userService.deleteOne("USR001")).thenReturn(true );

        this.mockMvc.perform( get("/users/{id}/delete" , "USR001").sessionAttr("authUser",user) )
                .andExpect( status().is( 302 ) )
                .andExpect( redirectedUrl("/users?msg=Successfully Deleted!"));

    }

    @Test
    public void getUpdatePageWithErrorTest() throws  Exception {
        when( this.userService.findById("USR001")).thenReturn( null );

        this.mockMvc.perform( get("/users/{id}/update" , "USR001").sessionAttr("authUser","user") )
                .andExpect( status().is(302))
                .andExpect( redirectedUrl("/users?msg=Something went wrong!"));
    }

    @Test
    public void getUpdatePageWithOkTest() throws Exception {
        when( this.userService.findById("USR001")).thenReturn( user );

        this.mockMvc.perform( get("/users/{id}/update" , "USR001").sessionAttr("authUser", user) )
                .andExpect( status().is(200))
                .andExpect( view().name("USR002"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void postUpdateUseWithBindErrorTest() throws  Exception {
        user.setName("");
        user.setEmail("");
        user.setPassword("");
        user.setConfirmPassword("");
        user.setRole("");

        this.mockMvc.perform( post("/users/{id}/update" , "USR001").sessionAttr("authUser",user).flashAttr( "user" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR002"))
                .andExpect( model().attributeExists("user"));
    }

    @Test
    public void postUpdateUserWithPasswordTest() throws  Exception {
        user.setName("name");
        user.setEmail("email");
        user.setPassword("123");
        user.setConfirmPassword("321");
        user.setRole("admin");

        this.mockMvc.perform( post("/users/{id}/update" , "USR001").sessionAttr("authUser",user).flashAttr( "user" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR002"))
                .andExpect( model().attributeExists("user" , "error"));
    }

    @Test
    public void postUpdateUserWithDuplicateEmailTest() throws  Exception {
        user.setId("USR001");
        user.setName("name");
        user.setEmail("email");
        user.setPassword("123");
        user.setConfirmPassword("321");
        user.setRole("admin");

        User savedUser = new User();
        savedUser.setId("USR002");

        when( this.userService.findById("email") ).thenReturn( savedUser );

        this.mockMvc.perform( post("/users/{id}/update" , "USR001").sessionAttr("authUser",user).flashAttr( "user" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR002"))
                .andExpect( model().attributeExists("user" , "error"));
    }

    @Test
    public void postUpdateUserWithUpdateErrorTest() throws  Exception {
        user.setId("USR001");
        user.setName("name");
        user.setEmail("email");
        user.setPassword("123");
        user.setConfirmPassword("321");
        user.setRole("admin");

        when( this.userService.findById("email") ).thenReturn( null );
        when( this.userService.updateOne( user )).thenReturn( false );

        this.mockMvc.perform( post("/users/{id}/update" , "USR001").sessionAttr("authUser",user).flashAttr( "user" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR002"))
                .andExpect( model().attributeExists("user" , "error"));
    }

    @Test
    public void postUpdateUserWithOkTest() throws  Exception {
        user.setId("USR001");
        user.setName("name");
        user.setEmail("email");
        user.setPassword("123");
        user.setConfirmPassword("321");
        user.setRole("admin");

        when( this.userService.findById("email") ).thenReturn( null );
        when( this.userService.updateOne( user )).thenReturn( true );

        this.mockMvc.perform( post("/users/{id}/update" , "USR001").sessionAttr("authUser",user).flashAttr( "user" , user ) )
                .andExpect( status().isOk() )
                .andExpect( view().name("USR002"))
                .andExpect( model().attributeExists("user" , "error"));
    }
}
