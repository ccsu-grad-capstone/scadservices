package edu.ccsu.cs595.capstone.scadservices.service;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.KeyFactorySpi;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.SignedJWT;
import edu.ccsu.cs595.capstone.scadservices.dao.UserDao;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.dto.UserListDto;
import edu.ccsu.cs595.capstone.scadservices.entity.User;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.util.GUIDGenerator;

import static edu.ccsu.cs595.capstone.scadservices.EndpointConstants.IDTOKEN;


@Stateless
public class UserService {
	
	@Inject
	UserDao userDao;
	
	@Inject
	SCADSecurityManager	sm;
	
	@Inject
	GUIDGenerator gen;

	private Client restClient = ClientBuilder.newClient();

	private static final String REST_URI = "https://api.login.yahoo.com";

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
//		String fName = jsonObject.get("given_name").getAsString();
//		String lName = jsonObject.get("family_name").getAsString();
//		String email = jsonObject.get("email").getAsString();
//		restClient.target(REST_URI)
//				  .path("openid/v1/userinfo")
//				  .request(MediaType.APPLICATION_JSON)
//				  .get(User.class);
// 		also need to somehow give access_token and id_token here.
//		String userGuid = gen.generateGUID(lName); // Need this change once we get user details from Yahoo.
//		AuditContext sc = new AuditContext(userGuid,lName);
		String fName = "Phil";
		String lName = "Murray";
//		userDto.setFirstName(fName);
//		userDto.setLastName(lName);
		userDto.setId(1L);
		return userDto;
		
	}
	
	private void entityToDto(User user, UserDto userDto) {
		
		if (Objects.nonNull(user)) {
			userDto.setId(user.getId());
//			userDto.setFirstName(user.getFirstName());
//			userDto.setLastName(user.getLastName());
			userDto.setEmail(user.getEmail());
//			userDto.setPassword(user.getPassword());
//			userDto.setUserGuid(user.getUserGuid());
//			userDto.setIsEmailVerified(user.getIsEmailVerified());
//			userDto.setIsUserDeleted(user.getIsUserDeleted());
			userDto.setCreatedBy(user.getCreatedBy());
			userDto.setCreatedAt(user.getCreatedAt());
			userDto.setModifiedBy(user.getModifiedBy());
			userDto.setModifiedAt(user.getModifiedAt());
		}
		
	}
}
