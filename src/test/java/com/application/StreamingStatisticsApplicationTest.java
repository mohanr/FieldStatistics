package com.application;

import com.field.controller.FieldController;
import com.field.controller.TelemetrySummaryException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StreamingStatisticsApplicationTest {

	Logger logger = LoggerFactory.getLogger("com.statistics");

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
	public void shouldRecordStream() throws Exception {


		Random r = new Random();
		for( int i = 0 ; i <  1000 ; i ++   ){

		String value = String.valueOf((int) ((Math.random() * 900) + 100) / 100.0);

		String field_Data =
			"{"
					+ "	\"vegetation\" : " + value + ","
					+ "	\"occurrenceAt\" : \"2019-04-23T08:50Z\""
					+ "}";
		//logger.debug( "Field value is {} " , field_Data );
		mvc.perform(
				MockMvcRequestBuilders.post("/field-conditions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(field_Data))
				.andExpect(status().isCreated());
		}
	}
	@Test
	public void shouldNotRecord() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.post("/field-conditions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(INVALID_FIELD_DATA))
				.andExpect(status().isBadRequest());
	}


	@Test(expected = Exception.class)
	public void shouldNotRecordDueToException() throws Exception {
		String field_Data =
				"{"
						+ "	\"vegetation\" : \"0.82\","
						+ "	\"occurrenceAt\" : \"2019-04-23T08:50\""
						+ "}";
		mvc.perform(
				MockMvcRequestBuilders.post("/field-conditions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(field_Data));
	}


}
