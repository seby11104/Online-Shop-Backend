package org.sda.finalbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sda.finalbackend.entity.User;
import org.sda.finalbackend.entity.UserRole;
import org.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import org.sda.finalbackend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get all users test")
    public void getAllUsersTest()
    {
        User user1= new User();
        user1.setId(1);
        user1.setEmail("test1@test.com");
        user1.setUsername("test1");
        user1.setUserRole(UserRole.ADMIN);
        user1.setPassword("1234");

        User user2= new User();
        user2.setId(2);
        user2.setEmail("test2@test.com");
        user2.setUsername("test2");
        user2.setUserRole(UserRole.ADMIN);
        user2.setPassword("1234");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> resultList = userService.findAll();

        assertEquals(2,resultList.size());
        assertEquals("test1",resultList.get(0).getUsername());

    }

    @Test
    @DisplayName("Create user test")
    public void createUserTest() throws Exception {
        User user1= new User();
        user1.setEmail("test1@test.com");
        user1.setUsername("test1");
        user1.setUserRole(UserRole.ADMIN);
        user1.setPassword("");

        userService.createUser(user1);
        verify(userRepository, times(1))
                .save(user1);



    }

}
