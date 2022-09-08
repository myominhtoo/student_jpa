package com.lionel.student_jpa.controller;

import com.lionel.student_jpa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRootController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getRootTest() throws Exception {
        this.mockMvc.perform( get("/").sessionAttr("authUser" , new User()) )
                .andExpect( view().name("MNU001"));
    }

    @Test
    public void getHomePageTest() throws Exception {
        this.mockMvc.perform( get("/home").sessionAttr("authUser" , new User()) )
                .andExpect( view().name("MNU001"));
    }

}
