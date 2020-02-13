package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.ccsu.cs595.capstone.scadservices.dao.UserDao;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.dto.UserListDto;
import edu.ccsu.cs595.capstone.scadservices.entity.AuditContext;
import edu.ccsu.cs595.capstone.scadservices.entity.User;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.util.GUIDGenerator;
import org.codehaus.jackson.map.util.JSONPObject;

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
	private static final String ACCESS_TOKEN = "yIZCP3We7w9uAwQi4_lcsimWjIIt.Y7T2bkXmI1GPwHyMOKQD272aMBHWF_QmuiRd7YzZpA..JMoyQ.wbu3MzIYwNPjVFNE7t62tVUIW_7x8Uy3E0WsbuhC7WdUwALEFyOLjPmTj1H8.dMDkcgHtdrYhS6Binekey5kgZ0pyZPfentnnCdXHSQU4kDgO4yhQvRQP3IobGS3z0vw4zt89dYMnwGUyv1uubzTS7Tfc86bgMlhShLT0JAxCW8qkLmvTBpGNk74jokShdKFmPU6pDNM8Z9fI8Hv5hCSudY_SDkBzvLW5VwEx2TiXt_.AA4N9KXnYz4MfTskTqBJoLT7LkUO1bFsPCUD6xALPuOjOHCdMKi9SFnxqtvivjtXpc0jxl0FQ2XH4vMFGZ1oY89Pe21LUTbV10y.qjedqUYJKUePTgEOtstPO4vnv_KX.Rd4NH6RQ5e1tvqtgsct6AXGFCqORG_yB1zMCdcJHnpjZS3IQ3B.TEIf6xfIM1F3dD2d2GADmNGa38O.rDf608Fpl97YhoKs7h6zmYyJ.Od2zaENijKcfehKspX0Oaxb6ixj2TNM_WwrhR6KtG82mbchlMUFTb6wS_pd4jXmYhdybWysIXy7RWmDjL1ztrjCrpAiolvvVXCKT4hesFPMqrpslqRFxlJkwNVrwUE7mL4hlomeqX11hqlucmSzqfgiZKmR3KzGYzQ4IQxLhzZSdw5or4bfLMprjig9GMfF9ZaBhZb4xaG5Zm1ZjmlU4vY4EyqhuzpKMRxNB7bGxgnLOvEHq2AgASHkmZ9sSlHpLD3RgXiObWc6w02yAnBHGR_3hqCuR_8MAQJcql2V8mH9axoMGPhcU0WMNqEtX5Z1AtkBkUAFTeVWFe.qoH0MuiY2o2REQfM7EV.3Hx.outntuiNjKz8r.1E89n0v15_LmrmSG9GOknhWMO9QpFweN2tiLRAguHKtYeYQXbbatXabesuLBqVHZ2jXstpBwjRdcbmiBEmbC_9H5Y3R85ZVHQyLeU0IB4meF0E4YVgU-";
	private static final String ID_TOKEN = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjM0NjZkNTFmN2RkMGM3ODA1NjU2ODhjMTgzOTIxODE2YzQ1ODg5YWQifQ.eyJhdF9oYXNoIjoiX0tkdGRSSUU2eTJEZWR2aW9jWjNyUSIsInN1YiI6IjJPTUxDVDNDMkE0MlozRkNHV0paQ0lEWUxVIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImJpcnRoZGF0ZSI6IjE5OTAiLCJpc3MiOiJodHRwczovL2FwaS5sb2dpbi55YWhvby5jb20iLCJwcm9maWxlX2ltYWdlcyI6eyJpbWFnZTY0IjoiaHR0cHM6Ly9zLnlpbWcuY29tL2FnL2ltYWdlcy81ODc3LzU5NDkwMzkyNTUyX2FjOWI2YV82NHNxLmpwZyIsImltYWdlMTkyIjoiaHR0cHM6Ly9zLnlpbWcuY29tL2FnL2ltYWdlcy81ODc3LzU5NDkwMzkyNTUyX2FjOWI2YV8xOTJzcS5qcGciLCJpbWFnZTEyOCI6Imh0dHBzOi8vcy55aW1nLmNvbS9hZy9pbWFnZXMvNTg3Ny81OTQ5MDM5MjU1Ml9hYzliNmFfMTI4c3EuanBnIiwiaW1hZ2UzMiI6Imh0dHBzOi8vcy55aW1nLmNvbS9hZy9pbWFnZXMvNTg3Ny81OTQ5MDM5MjU1Ml9hYzliNmFfMzJzcS5qcGcifSwiZ2l2ZW5fbmFtZSI6IlJ5YW4iLCJsb2NhbGUiOiJlbi1VUyIsIm5vbmNlIjoiODQyNjEzIiwicGljdHVyZSI6Imh0dHBzOi8vcy55aW1nLmNvbS9hZy9pbWFnZXMvNTg3Ny81OTQ5MDM5MjU1Ml9hYzliNmFfMTkyc3EuanBnIiwiYXVkIjoiZGoweUptazlhMXBCT0hWcGJsUnhNRTlQSm1ROVdWZHJPVnBGYURaV1ZteHlUa2N3YldOSGJ6bE5RUzB0Sm5NOVkyOXVjM1Z0WlhKelpXTnlaWFFtYzNZOU1DWjRQV0UzIiwiYXV0aF90aW1lIjoxNTgxNTIzNjk3LCJuYW1lIjoiUnlhbiBMYXV6b24iLCJuaWNrbmFtZSI6IlJ5YW4iLCJzZXNzaW9uX2V4cCI6MTU4MjczMzI5NywiZXhwIjoxNTgxNTQ3Njg4LCJpYXQiOjE1ODE1NDQwODgsImZhbWlseV9uYW1lIjoiTGF1em9uIiwiZW1haWwiOiJsYXV6b24yMzJAeWFob28uY29tIn0.6TFNnnpX4uQbtHkOgkWo2UYkt48Jvx5NyH5qCgt2oQ54JhITsYYwPSOeQLC1SlHWEabSSo1ECz7PmLW6sjn2VA";

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
//		if ((Objects.isNull(sm.getIDTOKEN())) || (Objects.isNull(sm.getACCESSTOKEN()))){
//			throw new RuntimeException("id_token or access_token are missing");
//		}
//		String[] splitString = sm.getIDTOKEN().split("\\.");
//		String josePayload = splitString[1];
//		String payload = new String(Base64.getDecoder().decode(josePayload));
//
//		JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();
//
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
