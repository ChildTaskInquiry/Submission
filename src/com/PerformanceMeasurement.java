package com;

public class PerformanceMeasurement {
	public static long start = System.currentTimeMillis();

	public void getPerformance(String message) {
		System.out.println(message + (System.currentTimeMillis() - start) + "ms");
		start = System.currentTimeMillis();
	}
}
