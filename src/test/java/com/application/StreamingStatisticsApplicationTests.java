package com.application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.field.controller.FieldController;

@RunWith(SpringRunner.class)
@WebMvcTest(FieldController.class)
public class StreamingStatisticsApplicationTests {

	
	   @Autowired
	   private MockMvc mvc;

	   @MockBean
	   private FieldController fieldController;

	   @Test
	    public void shouldReturnEmpty() throws Exception {
	        this.mvc.perform(post("/field-conditions")).andDo(print()).andExpect(status().isOk())
	        .andExpect(jsonPath("$").doesNotExist());
	    }
}
