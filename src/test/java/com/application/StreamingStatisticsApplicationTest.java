package com.application;

import com.field.controller.FieldController;
import io.restassured.mapper.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StreamingStatisticsApplicationTest {

	private final static String RESOLVED_FIELD_DATA =
			"{"
					+ "	\"vegetation\" : {"
			+ "\"min\" : \"0.01\","
			+ "	\"max\" : \"1.0\","
			+ "	\"avg\" : \"0.5\""
			+ "    }"
			+ "}";

	private final static String FIELD_DATA =
			"{"
					+ "	\"vegetation\" : \"0.82\","
			       + "	\"occurrenceAt\" : \"2019-04-23T08:50Z\""
				+ "}";

	private final static String INVALID_FIELD_DATA =
			"{"
					+ "	\"vegetation\" : \"-1\","
					+ "	\"occurrenceAt\" : \"2019-04-23T08:50Z\""
					+ "}";
	@Autowired
	   private MockMvc mvc;


	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.standaloneSetup(new FieldController()).build();
	}
	   @Test
	    public void shouldReturnEmpty() throws Exception {
	        this.mvc.perform(post("/field-conditions")).andDo(print()).andExpect(status().isBadRequest())
	        .andExpect(jsonPath("$").doesNotExist());
	    }

	@Test
	public void shouldRecord() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.post("/field-conditions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(FIELD_DATA))
				.andExpect(status().isCreated());
	}

	@Test
	public void shouldNotRecord() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.post("/field-conditions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(INVALID_FIELD_DATA))
				.andExpect(status().isBadRequest());
	}


}
