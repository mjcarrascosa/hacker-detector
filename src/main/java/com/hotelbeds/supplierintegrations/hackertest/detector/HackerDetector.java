package com.hotelbeds.supplierintegrations.hackertest.detector;

public interface HackerDetector {
	/**
	 * Parse a log line and return ip if it has risk or null if it hasn't.
	 * @param line Line to parse
	 * @return ip if has risk, null otherwise
	 */
	String parseLine(String line);
}
