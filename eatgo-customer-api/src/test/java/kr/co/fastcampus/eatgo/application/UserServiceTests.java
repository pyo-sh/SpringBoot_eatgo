package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTests {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerUser() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

    @Test
    public void registerUserWithExistedEmail(){
        String email = "tester@exmaple.com";
        String name = "Tester";
        String password = "test";

        User mockUser = User.builder().build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        assertThatThrownBy(() ->
            userService.registerUser(email, name, password)
        ).isInstanceOf(EmailExistedException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    public void authenticateWithValidAttributes(){
        String email = "tester@exmaple.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email)
                .build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertEquals(user.getEmail(), email);
    }

    @Test
    public void authenticateWithNotExistedEmail(){
        String email = "x@exmaple.com";
        String password = "test";

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThatThrownBy(() ->
            userService.authenticate(email, password)
        ).isInstanceOf(EmailNotExistedException.class);
    }

    @Test
    public void authenticateWithWrongPassword(){
        String email = "tester@exmaple.com";
        String password = "x";

        User mockUser = User.builder()
                .email(email)
                .build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(false);

        assertThatThrownBy(() ->
            userService.authenticate(email, password)
        ).isInstanceOf(PasswordWrongException.class);
    }
}