package com.tourGuide.userservice.service;

import com.tourGuide.userservice.exception.ResourceNotFoundException;
import com.tourGuide.userservice.model.NewUserDto;
import com.tourGuide.userservice.model.User;
import com.tourGuide.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepositoryMock;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void createUser() {


        NewUserDto userDto = new NewUserDto("userNameTet", "phoneTest", "emailTest");
        doNothing().when(userRepositoryMock).addUser(any());

        User result = userService.createUser(userDto);

        assertThat(result.getUserName()).isEqualTo(userDto.userName());
        assertThat(result.getEmailAddress()).isEqualTo(userDto.emailAddress());
        assertThat(result.getPhoneNumber()).isEqualTo(userDto.phoneNumber());
        assertThat(result.getUserId()).isNotNull();
    }

    @Test
    void getUserByUserName() {

//        appel le repo avec la bonne donnée
        // renvoi la bonne donnée
//        renvoi la bonne exception
        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, "userNameTet", "phoneTest", "emailTest");

        when(userRepositoryMock.getUserByUsername(Mockito.anyString())).thenReturn(user);
        User result = userService.getUserByUserName("userNameTest");

        assertThat(result).isEqualTo(user);
        verify(userRepositoryMock, times(1)).getUserByUsername("userNameTest");
    }

    @Test
    void getUserNameByUserName_ShouldThrowResourceNotFoundException() {
        doThrow(ResourceNotFoundException.class).when(userRepositoryMock).getUserByUsername(anyString());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByUserName("username"));
    }

    @Test
    void getAllUsers() {

        // renvoi les bonnes données

        // renvoi la bonne exception

        List<User> users = new ArrayList<>();
        IntStream.range(0, 5)
                .forEach(i -> users.add(new User(UUID.randomUUID(),
                        "userNameTet" + i,
                        "phoneTest" + i,
                        "emailTest")));

        when(userRepositoryMock.getAllUsers()).thenReturn(users);
        List<User> result = userService.getAllUsers();

        assertThat(result).isEqualTo(users);
        verify(userRepositoryMock, times(1)).getAllUsers();
    }

    @Test
    void getAllUsers_ShouldThrowResourceNotFoundException() {

        when(userRepositoryMock.getAllUsers()).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> userService.getAllUsers());
    }
}