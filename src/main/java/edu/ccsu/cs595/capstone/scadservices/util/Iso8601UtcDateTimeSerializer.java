package edu.ccsu.cs595.capstone.scadservices.util;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * For use in \@JsonSerializer bean field annotation.
 */
public class Iso8601UtcDateTimeSerializer extends JsonSerializer<ZonedDateTime> {
	
	@Override
	public void serialize(ZonedDateTime arg0, JsonGenerator arg1,
			SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		ZonedDateTime value = arg0.withZoneSameInstant(ZoneId.of("UTC"));
		String result = ISO_OFFSET_DATE_TIME.format(value);
		arg1.writeString(result);
	}

}
