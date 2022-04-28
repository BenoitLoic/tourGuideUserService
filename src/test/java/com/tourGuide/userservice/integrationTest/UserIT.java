package com.tourGuide.userservice.integrationTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourGuide.userservice.exception.DataAlreadyExistException;
import com.tourGuide.userservice.exception.IllegalArgumentException;
import com.tourGuide.userservice.exception.ResourceNotFoundException;
import com.tourGuide.userservice.model.NewUserDto;
import com.tourGuide.userservice.model.User;
import com.tourGuide.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.validation.ValidationException;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addUser() throws Exception {
        // GIVEN
        NewUserDto newUser = new NewUserDto("usernameTest", "phoneTest", "emailtzet");
        String jsonString = objectMapper.writeValueAsString(newUser);
        // WHEN

        // THEN
        mockMvc
                .perform(post("/addUser")
                        .content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(result.getResponse().getContentAsString().contains("usernameTest")).isTrue());

        mockMvc
                .perform(post("/addUser")
                        .content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof DataAlreadyExistException).isTrue());


        String jsonNotValid = jsonString.replace("usernameTest", " ");

        mockMvc
                .perform(post("/addUser")
                        .content(jsonNotValid).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof MethodArgumentNotValidException).isTrue());

    }

    @Test
    void getAllUsers() throws Exception {
        // GIVEN

        // WHEN

        // THEN
        mockMvc
                .perform(get("/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(result ->
                        assertThat(
                                objectMapper.readValue(result.getResponse().getContentAsString(), User[].class).length)
                                .isGreaterThan(100))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void getUserByUserName() throws Exception {

        // GIVEN
        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, "usertest", "phone", "myTestMail");
        userRepository.addUser(user);
        // WHEN

        // THEN
        mockMvc
                .perform(get("/getUserByUsername")
                        .param("username", "usertest"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(user),true));

        mockMvc
                .perform(get("/getUserByUsername")
                        .param("username","cetUtilisateurExistePAS"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof ResourceNotFoundException).isTrue());

        mockMvc
                .perform(get("/getUserByUsername")
                        .param("username",""))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof IllegalArgumentException).isTrue());


    }

}
