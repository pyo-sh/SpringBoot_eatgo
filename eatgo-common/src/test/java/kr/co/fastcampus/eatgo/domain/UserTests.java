package kr.co.fastcampus.eatgo.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserTests {
    @Test
    public void creation(){
        User user = User.builder()
                .email("tester@example.com")
                .name("테스터")
                .level(100L)
                .build();

        assertEquals(user.getName(), "테스터");
        assertEquals(user.isAdmin(), true);
        assertEquals(user.isActive(), true);

        user.deactivate();

        assertEquals(user.isActive(), false);
    }
}
