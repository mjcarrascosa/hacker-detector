package com.hotelbeds.supplierintegrations.hackertest.detector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hotelbeds.supplierintegrations.hackertest.entity.FailedLogin;
import com.hotelbeds.supplierintegrations.hackertest.repository.RiskRepository;

@ExtendWith(MockitoExtension.class)
public class HackDetectorImplTest {
	@InjectMocks
	private HackerDetectorImpl hackDetector;

	@Mock
	private RiskRepository riskRepository;


	@Test
	public void when_sign_success_login_has_no_risk() {
		String ip = hackDetector.parseLine("192.168.1.1,1623935651,SIGNIN_SUCCESS,username");
		assertThat(ip).isNull();
	}
	@Test
	public void when_sign_failed_login_has_no_risk() {
		when(riskRepository.save(any())).thenReturn(null);
		when(riskRepository.findByIp("192.168.1.1")).thenReturn(populateResponseOneItem());
		String ip = hackDetector.parseLine("192.168.1.1,1623935651,SIGNIN_FAILURE,username");
		assertThat(ip).isNull();
	}

	@Test
	public void when_sign_failed_login_has_risk() {
		when(riskRepository.save(any())).thenReturn(null);
		when(riskRepository.findByIp("192.168.1.1")).thenReturn(populateResponseFiveItem());
		String ip = hackDetector.parseLine("192.168.1.1,1623935651,SIGNIN_FAILURE,username");
		assertThat(ip).isEqualTo("192.168.1.1");
	}
	private static FailedLogin populateFailedLogin(String ip, long timestamp) {
		FailedLogin failedLogin = new FailedLogin();
		failedLogin.setIp(ip);
		failedLogin.setTimestamp(timestamp);
		return failedLogin;
	}

	private static List<FailedLogin> populateResponseOneItem() {
		return Arrays.asList(populateFailedLogin("192.168.1.1", 1623935651));
	}

	private static List<FailedLogin> populateResponseFiveItem() {
		return Arrays.asList(populateFailedLogin("192.168.1.1", 1623935651),
				populateFailedLogin("192.168.1.1", 1623935652), populateFailedLogin("192.168.1.1", 1623935653),
				populateFailedLogin("192.168.1.1", 1623935654), populateFailedLogin("192.168.1.1", 1623935655));
	}
}
