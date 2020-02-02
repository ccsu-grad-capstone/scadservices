package edu.ccsu.cs595.capstone.scadservices.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * A JPA Entity with version and ID.
 */
@MappedSuperclass
public abstract class BasicEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long version;

	/**
	 * Create a new entity at version zero (0).
	 */
	public BasicEntity() {
		super();
		version = 0L;
	}

	/**
	 * Same as Object.equals(..) read carefully why.
	 * http://stackoverflow.com/questions/5031614/the-jpa-hashcode-equals-dilemma#18776798
	 *
	 * @return True if the two entities are the same object in memory.
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Transient
	public String getDescriptor() {
		return this.getClass().getSimpleName() + "@" + this.getId();
	}

	/**
	 * Get the ID of this entity.
	 *
	 * @return The ID
	 */
	@Transient
	public abstract Long getId();

	/**
	 * Set the ID of this entity.
	 *
	 * @param id The ID to set.
	 */
	public abstract void setId(Long id);

	/**
	 * Get the entity version.
	 *
	 * @return The entity version
	 */
	@Version
	@Column(name = "vsn", nullable = false)
	public Long getVersion() {
		return version;
	}

	/**
	 * Set the entity version.
	 *
	 * @param version The new entity version
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * Same as Object.hashCode() read carefully why.
	 * http://stackoverflow.com/questions/5031614/the-jpa-hashcode-equals-dilemma#18776798
	 *
	 * @return The hash code
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
