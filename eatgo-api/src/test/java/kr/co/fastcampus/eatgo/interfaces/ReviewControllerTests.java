package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTests {
    @Autowired
    MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void create() throws Exception{
        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"JOKER\", \"score\": 3, \"description\":\"Mat-it-da\"}"))
                .andExpect(status().isCreated());

        verify(reviewService).addReview(any());
    }
}