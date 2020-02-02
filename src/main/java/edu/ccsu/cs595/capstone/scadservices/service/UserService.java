package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.ccsu.cs595.capstone.scadservices.dao.UserDao;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.entity.User;

@Stateless
public class UserService {
	
	@Inject
	UserDao userDao;
	
	public UserDto getUser(String email) {
		
		UserDto userDto = null;
		User user = userDao.getUserByEmail(email);
		if (Objects.nonNull(user)) {
			userDto = new UserDto();
			this.entityToDto(user, userDto);
		}

		return userDto;
		
	}
	
	private void entityToDto(User user, UserDto userDto) {
		
		if (Objects.nonNull(user)) {
			userDto.setId(user.getId());
			userDto.setFirstName(user.getFirstName());
			userDto.setLastName(user.getLastName());
			userDto.setEmail(user.getEmail());
			userDto.setPassword(user.getPassword());
			userDto.setUserGuid(user.getUserGuid());
			userDto.setIsEmailVerified(user.getIsEmailVerified());
			userDto.setIsUserDeleted(user.getIsUserDeleted());
			userDto.setCreatedBy(user.getCreatedBy());
			userDto.setCreatedAt(user.getCreatedAt());
			userDto.setModifiedBy(user.getModifiedBy());
			userDto.setModifiedAt(user.getModifiedAt());
		}
		
	}

}
