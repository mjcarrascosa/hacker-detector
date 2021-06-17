package com.hotelbeds.supplierintegrations.hackertest.detector;

import org.springframework.stereotype.Service;

import com.hotelbeds.supplierintegrations.hackertest.entity.FailedLogin;
import com.hotelbeds.supplierintegrations.hackertest.repository.RiskRepository;

@Service
public class HackerDetectorImpl implements HackerDetector {
	private RiskRepository riskRepository;

	public HackerDetectorImpl(RiskRepository riskRepository) {
		this.riskRepository = riskRepository;
	}

	@Override
	public String parseLine(String line) {
		String[] lineParts = line.split(",");
		if ("SIGNIN_FAILURE".equals(lineParts[2])) {
			riskRepository.save(createFailedLogin(lineParts[0], lineParts[1]));
			return riskRepository.findByIp(lineParts[0]).size() >= 5 ? lineParts[0] : null;
		}
		return null;
	}

	private static FailedLogin createFailedLogin(String ip, String timestamp) {
		FailedLogin login = new FailedLogin();
		login.setIp(ip);
		login.setTimestamp(Long.parseLong(timestamp));
		return login;
	}

}
