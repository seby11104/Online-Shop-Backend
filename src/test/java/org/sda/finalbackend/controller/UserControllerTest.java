package org.sda.finalbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sda.finalbackend.entity.User;
import org.sda.finalbackend.entity.UserRole;
import org.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import org.sda.finalbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("User creat cu succes")
    public void createUserTest() throws Exception {
        User user= new User();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setUsername("test");
        user.setUserRole(UserRole.ADMIN);
        user.setPassword("1234");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.username").value("test"))
                .andExpect(jsonPath("$.data.userRole").value("ADMIN"));

    }

    @Test
    @DisplayName("Get Users")
    public void getAllUsersTest() throws Exception {
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

        Mockito.when(userService.findAll()).thenReturn(userList);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", IsCollectionWithSize.hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].username").value("test1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].username").value("test2"));

    }

}
