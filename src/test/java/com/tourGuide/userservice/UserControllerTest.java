package com.tourGuide.userservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tourGuide.userservice.model.User;
import com.tourGuide.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class UserControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userServiceMock;

    @Test
    public void createUserValid() throws Exception {

        User userTest = new User();
        userTest.setUserId(UUID.randomUUID());
        userTest.setUserName("usernameTest");
        userTest.setEmailAddress("emailTest");
        userTest.setPhoneNumber("3216350");

        ObjectMapper obj = new ObjectMapper();
        ObjectNode jsonUser = obj.createObjectNode();
        jsonUser.set("userId", TextNode.valueOf(userTest.getUserId().toString()));
        jsonUser.set("userName", TextNode.valueOf(userTest.getUserName()));
        jsonUser.set("phoneNumber", TextNode.valueOf(userTest.getPhoneNumber()));
        jsonUser.set("emailAddress", TextNode.valueOf(userTest.getEmailAddress()));
        System.out.println(jsonUser);
        mockMvc.perform(post("/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser.toString()))
                .andExpect(status().isCreated());

    }

}
