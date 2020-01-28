package edu.ccsu.cs595.capstone.scadservices.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * This class parses and creates a Timestamp with Timezone string for consumption
 * by MySQL with the to_timestamp(string,'YYYY-MM-DD"T"HH24:MI:SS.FF3 TZHTZM').
 */
public class Iso8601ZonedDateTimeAdapter  extends XmlAdapter<String, ZonedDateTime> {
	
	private static final ThreadLocal<DateTimeFormatter> iso8601 = new ThreadLocal<DateTimeFormatter>() {
		@Override
		protected DateTimeFormatter initialValue() {
			return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
		}
	};

	@Override
	public String marshal(ZonedDateTime arg0) throws Exception {
		return iso8601.get().format(arg0.withZoneSameInstant(ZoneOffset.UTC));
	}

	@Override
	public ZonedDateTime unmarshal(String arg0) throws Exception {
		return ZonedDateTime.parse(arg0,iso8601.get());
	}

}
