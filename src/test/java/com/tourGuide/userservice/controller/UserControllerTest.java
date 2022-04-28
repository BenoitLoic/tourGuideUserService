package com.tourGuide.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tourGuide.userservice.exception.IllegalArgumentException;
import com.tourGuide.userservice.exception.ResourceNotFoundException;
import com.tourGuide.userservice.model.NewUserDto;
import com.tourGuide.userservice.model.User;
import com.tourGuide.userservice.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static UUID uuid;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userServiceMock;

    @BeforeAll
    static void beforeAll() {
        uuid = UUID.randomUUID();
    }

    @Test
    void createUserValid() throws Exception {

        // GIVEN
        NewUserDto userTest = new NewUserDto("usernameTest", "3216350", "emailTest");


        User userResponse = new User(uuid, "userNameTest", "3216350", "emailTest");


        String jsonUser = objectMapper.writeValueAsString(userTest);
        System.out.println(jsonUser);
        System.out.println(objectMapper.writeValueAsString(userResponse));
//        ObjectNode jsonUser = obj.createObjectNode();
//        jsonUser.set("userId", TextNode.valueOf(userTest.getUserId().toString()));
//        jsonUser.set("userName", TextNode.valueOf(userTest.getUserName()));
//        jsonUser.set("phoneNumber", TextNode.valueOf(userTest.getPhoneNumber()));
//        jsonUser.set("emailAddress", TextNode.valueOf(userTest.getEmailAddress()));

        // WHEN
        when(userServiceMock.createUser(userTest)).thenReturn(userResponse);

        // THEN
        mockMvc
                .perform(post("/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(userResponse)));

    }

    @Test
    void createUserInvalid() throws Exception {

        // GIVEN
        NewUserDto user = new NewUserDto(" ", "0123456", "emailTest");
        String userJson = objectMapper.writeValueAsString(user);
        NewUserDto userNullField = new NewUserDto(null, "0123456", "emailTest");
        String userNullJson = objectMapper.writeValueAsString(userNullField);
        // WHEN

        // THEN
        mockMvc
                .perform(post("/addUser")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

        mockMvc
                .perform(post("/addUser")
                        .content(userNullJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void getAllUsers() throws Exception {

        // GIVEN
        List<User> userList = new ArrayList<>();
        IntStream.range(0, 3)
                .forEach(i -> userList.add(
                        new User(UUID.randomUUID(),
                                "userNameTest" + i,
                                "phoneNumberTest" + i,
                                "emailAddress" + i)));

        String jsonResponse = objectMapper.writeValueAsString(userList);
        // WHEN
        when(userServiceMock.getAllUsers()).thenReturn(userList);
        // THEN
        mockMvc
                .perform(get("/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponse))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAllUsers_ShouldThrowResourceNotFoundException() throws Exception {
        // GIVEN

        // WHEN
        doThrow(ResourceNotFoundException.class).when(userServiceMock).getAllUsers();
        // THEN
        mockMvc
                .perform(get("/getAllUsers"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
    }

    @Test
    void getUserByUsernameValid() throws Exception {

        // GIVEN
        User user = new User(uuid, "userNameTest", "3216350", "emailTest");
        String jsonResponse = objectMapper.writeValueAsString(user);
        // WHEN
        when(userServiceMock.getUserByUserName(Mockito.anyString())).thenReturn(user);
        // THEN
        mockMvc
                .perform(get("/getUserByUsername")
                        .param("username", "userNameTest"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponse))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userServiceMock, times(1)).getUserByUserName("userNameTest");

    }

    @Test
    void getUserByUsernameInvalid() throws Exception {

        // GIVEN
        String username = "";
        // WHEN

        // THEN
        mockMvc
                .perform(get("/getUserByUsername")
                        .param("username", username))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException));
    }

    @Test
    void getUserByUsernameValid_ShouldThrowResourceNotFoundException() throws Exception {

        // GIVEN

        // WHEN
        doThrow(ResourceNotFoundException.class).when(userServiceMock).getUserByUserName(Mockito.anyString());
        // THEN
        mockMvc
                .perform(get("/getUserByUsername")
                        .param("username", "usernameTest"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
    }
}