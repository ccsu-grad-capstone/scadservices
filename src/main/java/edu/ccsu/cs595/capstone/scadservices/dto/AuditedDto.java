package edu.ccsu.cs595.capstone.scadservices.dto;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import edu.ccsu.cs595.capstone.scadservices.util.Iso8601DateTimeDeserializer;
import edu.ccsu.cs595.capstone.scadservices.util.Iso8601UtcDateTimeSerializer;
import edu.ccsu.cs595.capstone.scadservices.util.Iso8601ZonedDateTimeAdapter;

/**
 * A REST Audited representation with an audit columns
 */
public abstract class AuditedDto extends BasicDto {
	
	private ZonedDateTime createdAt;
	private String createdBy;
	private ZonedDateTime modifiedAt;
	private String modifiedBy;

    @XmlElement
    @XmlJavaTypeAdapter(Iso8601ZonedDateTimeAdapter.class)
	@JsonSerialize(using = Iso8601UtcDateTimeSerializer.class)
	@JsonDeserialize(using = Iso8601DateTimeDeserializer.class)	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    @XmlElement
    @XmlJavaTypeAdapter(Iso8601ZonedDateTimeAdapter.class)
	@JsonSerialize(using = Iso8601UtcDateTimeSerializer.class)
	@JsonDeserialize(using = Iso8601DateTimeDeserializer.class)
	public ZonedDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(ZonedDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
