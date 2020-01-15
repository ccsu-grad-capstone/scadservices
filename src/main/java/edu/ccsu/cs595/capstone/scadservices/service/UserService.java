package edu.ccsu.cs595.capstone.scadservices.service;

import javax.ejb.Stateless;

import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;

@Stateless
public class UserService {
	
	public UserDto getUser(String email) {
		
		UserDto userDto = new UserDto();
		
		userDto.setEmail("rk@my.ccsu.edu");
		userDto.setFirstName("Ramesh");
		userDto.setLastName("Kappera");
		userDto.setUserName("rkappera");
		userDto.setId(1l);
		
		return userDto;
		
	}

}
