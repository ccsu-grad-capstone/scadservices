package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.ccsu.cs595.capstone.scadservices.dao.UserDao;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.dto.UserListDto;
import edu.ccsu.cs595.capstone.scadservices.entity.AuditContext;
import edu.ccsu.cs595.capstone.scadservices.entity.User;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.util.GUIDGenerator;

@Stateless
public class UserService {
	
	@Inject
	UserDao userDao;
	
	@Inject
	SCADSecurityManager	sm;
	
	@Inject
	GUIDGenerator gen;
	
	public UserDto getUser(String email) {
		
		UserDto userDto = null;
		User user = userDao.getUserByEmail(email);
		if (Objects.nonNull(user)) {
			userDto = new UserDto();
			this.entityToDto(user, userDto);
		}

		return userDto;
		
	}
	
	public UserListDto getAllUsers() {
		
		UserListDto result = null;
		List<User> users = userDao.getAllUsers();
		if (Objects.nonNull(users)) {
			result = new UserListDto();
			List<UserDto> usersDto = new LinkedList<UserDto>();
			for (User u : users) {
				UserDto userDto = new UserDto();
				this.entityToDto(u, userDto);
				usersDto.add(userDto);
			}
			result.setUsers(usersDto);
		}

		return result;
		
	}
	
	public Boolean isValidUser(String email, String password) {
		
		Boolean result = false;
		User user = userDao.getUserByEmailAndPwd(email, password);
		if (Objects.nonNull(user)) {
			result = true;
		}

		return result;
		
	}
	
	public UserDto createUser(UserDto proposed) {
		
		UserDto result = null;
		return result;
		
	}
	
	@SuppressWarnings("static-access")
	public UserDto getUserInfo() {
		
		UserDto userDto =  new UserDto();
		String fName = "Ramesh";
		String lName = "Kappera";
		if ((Objects.isNull(sm.getIDTOKEN())) || (Objects.isNull(sm.getACCESSTOKEN()))){
			throw new RuntimeException("id_token or access_token are missing");
		}
		String userGuid = gen.generateGUID(lName); // Need this change once we get user details from Yahoo.
		AuditContext sc = new AuditContext(userGuid,lName);
		userDto.setFirstName(fName);
		userDto.setLastName(lName);
		userDto.setId(1L);
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
