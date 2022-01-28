package com.example.demo2;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo2.controller.FoodController;
import com.example.demo2.service.FoodService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { FoodController.class, FoodService.class})
@WebMvcTest
public class Demo2ApplicationTests {

	// private final static String TEST_USER_ID = "user-id-123";

	@Autowired
    private MockMvc mockMvc;

	@Test
	void contextTest() throws Exception {
		testJson();
	}

	private void testJson() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/foods")
                // .with(user(TEST_USER_ID))
                // .with(csrf())
                //.content(birthday)
                //.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultJson = result.getResponse().getContentAsString();
        assertNotNull(resultJson);
        assertEquals("", resultJson);
    }
}
