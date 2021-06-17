package com.hotelbeds.supplierintegrations.hackertest.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetector;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HackertestRestController.class)
public class HackertestRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HackerDetector hackerDetector;

	@Test
	public void when_login_failed_has_risk() throws Exception {
		when(hackerDetector.parseLine("192.168.1.1,1623935651,SIGNIN_FAILURE,username")).thenReturn("192.168.1.1");
		MvcResult result = mockMvc
				.perform(post("/v1/logline").param("line", "192.168.1.1,1623935651,SIGNIN_FAILURE,username"))
				.andExpect(status().isOk()).andReturn();
		String body = result.getResponse().getContentAsString();
		assertThat(body).isEqualTo("192.168.1.1");
	}

	@Test
	public void when_login_failed_has_no_risk() throws Exception {
		when(hackerDetector.parseLine("192.168.1.1,1623935651,SIGNIN_FAILURE,username")).thenReturn(null);
		MvcResult result = mockMvc
				.perform(post("/v1/logline").param("line", "192.168.1.1,1623935651,SIGNIN_FAILURE,username"))
				.andExpect(status().isOk()).andReturn();
		String body = result.getResponse().getContentAsString();
		assertThat(body).isEmpty();
	}

	@Test
	public void when_login_success_has_no_risk() throws Exception {
		MvcResult result = mockMvc
				.perform(post("/v1/logline").param("line", "192.168.1.1,1623935651,SIGNIN_SUCCESS,username"))
				.andExpect(status().isOk()).andReturn();
		String body = result.getResponse().getContentAsString();
		assertThat(body).isEmpty();
	}
}
