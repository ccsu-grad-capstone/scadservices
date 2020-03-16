package edu.ccsu.cs595.capstone.scadservices.dto;

import java.util.LinkedList;
import java.util.List;

public class UserListDto {
	
	private List<UserDto> users = new LinkedList<UserDto>();
	
	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserListDto [users=" + users + "]";
	}

}
