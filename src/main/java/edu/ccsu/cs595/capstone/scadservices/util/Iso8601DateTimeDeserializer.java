package edu.ccsu.cs595.capstone.scadservices.util;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * For use in \@JsonDeserializer bean field annotation.
 */
public class Iso8601DateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
	
	@Override
	public ZonedDateTime deserialize(JsonParser arg0,
			DeserializationContext arg1) throws IOException, JsonProcessingException {
		String value = arg0.getText();
		ZonedDateTime result = ZonedDateTime.from(ISO_DATE_TIME.parse(value)).
				withZoneSameInstant(ZoneId.of("UTC"));
		return result;
	}

}
