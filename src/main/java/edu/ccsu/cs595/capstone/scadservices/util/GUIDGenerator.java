package edu.ccsu.cs595.capstone.scadservices.util;

import java.util.Objects;
import java.util.UUID;

public class GUIDGenerator {
	
	public String generateGUID(String lastName) {
		
		String result = null;
		UUID guid = null;
		
		if (Objects.nonNull(lastName)) {
			guid = UUID.nameUUIDFromBytes(lastName.getBytes());
		} else {
			guid = UUID.randomUUID();
		}
		result = guid.toString();
		
		return result;
		
	}

}
