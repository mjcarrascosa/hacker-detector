package com.hotelbeds.supplierintegrations.hackertest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetector;

@RestController
public class HackertestRestController {

	@Autowired
	private HackerDetector riskService;
	
	@PostMapping("/v1/logline")
	public String log(@RequestParam String line) {
		return riskService.parseLine(line);
	}
}
