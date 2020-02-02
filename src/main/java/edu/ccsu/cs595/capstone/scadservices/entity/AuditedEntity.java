package edu.ccsu.cs595.capstone.scadservices.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * A JPA Entity that includes automatic audit fields.
 */
@MappedSuperclass
public abstract class AuditedEntity extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ZonedDateTime createdAt;
	private String createdBy;
	private ZonedDateTime modifiedAt;
	private String modifiedBy;

	/**
	 * Get the date and time this entity was created.
	 *
	 * @return ZonedDateTime this entity was created.
	 */
	@Column(name = "created_time")
	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * Set the date and time this entity was created.
	 *
	 * @param createdAt The ZonedDateTime
	 */
	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Get the GUID of the user who created this entity.
	 *
	 * @return The user's GUID
	 */
	@Column(name = "created_by")
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set the GUID of the user who created this entity.
	 *
	 * @param userGuid The user's GUID
	 */
	public void setCreatedBy(String userGuid) {
		this.createdBy = userGuid;
	}

	/**
	 * Get the date and time this entity was last modified.
	 *
	 * @return ZonedDateTime this entity was modified.
	 */
	@Column(name = "modified_time")
	public ZonedDateTime getModifiedAt() {
		return modifiedAt;
	}

	/**
	 * Set the date and time this entity was last modified.
	 *
	 * @param modifiedAt
	 */
	public void setModifiedAt(ZonedDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	/**
	 * Get the GUID of the user who last modified this entity.
	 *
	 * @return The GUID of the user who last modified this entity.
	 */
	@Column(name = "modified_by")
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Set the GUID of the user who last modified this entity.
	 *
	 * @param userGuid The user's GUID
	 */
	public void setModifiedBy(String userGuid) {
		this.modifiedBy = userGuid;
	}

	/**
	 * Callback causing JPA to update the audit fields upon persist and update.
	 *
	 * The JPA @PrePersist and @PreUpdate may be ignored in Spring-ORM. Call this
	 * explicitly in that case.
	 */
	@PrePersist
	@PreUpdate
	public void updateAuditFields() {
		AuditContext sc = AuditContext.getAuditContext();
		String userGuid = sc.getUserGuid();
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
		if (getCreatedBy() == null) {
			setCreatedBy(userGuid);
		}
		if (getCreatedAt() == null) {
			setCreatedAt(now);
		}
		setModifiedBy(userGuid);
		setModifiedAt(now);
	}

}
