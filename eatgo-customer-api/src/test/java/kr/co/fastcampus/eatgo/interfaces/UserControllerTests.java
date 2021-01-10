package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTests {
    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception{
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        User mockUser = User.builder()
                .Id(1004L)
                .email(email)
                .name(name)
                .password(password)
                .build();

        given(userService.registerUser(email, name, password))
                .willReturn(mockUser);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\" : \"tester@example.com\", \"name\" : \"Tester\", \"password\" : \"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/users/1004"));

        verify(userService).registerUser(eq(email), eq(name), eq(password));
    }

}