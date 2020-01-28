package edu.ccsu.cs595.capstone.scadservices.dto;

/**
 * A REST resource representation with an ID
 */
public abstract class BasicDto {
	
	public abstract Long getId();

	public abstract void setId(Long id);

}
