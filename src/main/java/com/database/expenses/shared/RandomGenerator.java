package com.database.expenses.shared;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class RandomGenerator {
	private final int UUID_LENGTH = 32;

	public String generateExpenseId(int length) {
		return generateRandomString(length);
	}

	// TODO: Support more than 32 characters
	private String generateRandomString(int length) {
		return generateRandomUUIDString(length).substring(0, length);
	}
	private String generateRandomUUIDString(int length) {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
