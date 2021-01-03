package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception{
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("JOKER House")
                .address("Seoul")
                .build());

        given(restaurantService.getRestaurants("Seoul", 1L)).willReturn(restaurants);

        mvc.perform(get("/restaurants?region=Seoul&category=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"JOKER House\"")));
    }

    @Test
    public void detailWithExisted() throws Exception{
        // 직접 만든 부분들을 확인 했지만 Spring data JPA 활용을 해서 실행하는 환경이 만들어져서 간편하게 만듬
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("JOKER House")
                .address("Seoul")
                .build();
        restaurant.setMenuItems(Arrays.asList(MenuItem.builder()
                .name("Kimchi")
                .build()));
        Review review = Review.builder()
                .name("JOKER")
                .score(5)
                .description("Great!")
                .build();
        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"JOKER House\"")))
                .andExpect(content().string(containsString("Kimchi")))
                .andExpect(content().string(containsString("Great!")));
    }
    @Test
    public void detailWithNotExisted() throws Exception{
        given(restaurantService.getRestaurant(1000001L))
            .willThrow(new RestaurantNotFoundException(1000001L));
        mvc.perform(get("/restaurants/1000001"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }
}
