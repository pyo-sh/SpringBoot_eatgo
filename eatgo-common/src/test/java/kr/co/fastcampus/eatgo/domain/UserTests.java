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
        assertTrue(user.isAdmin());
        assertTrue(user.isActive());

        user.deactivate();

        assertFalse(user.isActive());
    }

    @Test
    public void restaurantOwner() {
        User user = User.builder()
                .level(1L)
                .build();

        assertFalse(user.isRestaurantOwner());

        user.setRestaurantId(1004L);

        assertTrue(user.isRestaurantOwner());
        assertEquals(user.getRestaurantId(), 1004L);
    }
}
