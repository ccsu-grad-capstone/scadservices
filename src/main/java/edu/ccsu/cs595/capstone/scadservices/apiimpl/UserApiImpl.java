package edu.ccsu.cs595.capstone.scadservices.apiimpl;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.SignedJWT;

import edu.ccsu.cs595.capstone.scadservices.api.UserApi;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;
import edu.ccsu.cs595.capstone.scadservices.service.UserService;

public class UserApiImpl implements UserApi {

	@Inject
	UserService usrSvc;

	@Inject
	SCADSecurityManager sm;

	//
//	@Override
//	public Response getAll() {
//
//		UserListDto result = usrSvc.getAllUsers();
//
//		if (Objects.isNull(result)) {
//
//			return Response.status(Response.Status.NOT_FOUND).build();
//		}
//
//		return Response.status(Response.Status.OK).entity(result).build();
//
//	}
//
//	@Override
//	public Response isValidUser(String email, String password) {
//
//		Boolean result = usrSvc.isValidUser(email, password);
//
//		return Response.status(Response.Status.OK).entity(result).build();
//
//	}
//
//	@Override
//	public Response create(UserDto proposed) throws MissingParameterException, RuntimeException {
//
//		String missingParam = getMissingRequiredParams(proposed);
//		if (Objects.nonNull(missingParam)) {
//			throw new MissingParameterException(missingParam);
//		} else {
//			try {
//		    	UserDto result = usrSvc.createUser(proposed);
//		    	return Response.status(Response.Status.OK).entity(result).build();
//			} catch (Exception e) {
//				return Response.ok(e.getMessage()).build();
//			}
//		}
//		
//	}
//
//	@Override
//	public Response update(Long id, UserDto proposed) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Response delete(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	String getMissingRequiredParams(UserDto proposed) {
//
//		String missingParam = null;
//		if (Objects.isNull(proposed)) {
//			missingParam = "proposed object";
//		} else {
//			if (Objects.isNull(proposed.getFirstName())) {
//				missingParam = "first name";
//			} else if (Objects.isNull(proposed.getLastName())) {
//				missingParam = "last name";
//			} else if (Objects.isNull(proposed.getEmail())) {
//				missingParam = "email";
//			} else if (Objects.isNull(proposed.getPassword())) {
//				missingParam = "password";
//			}
//
//		}
//
//		return missingParam;
//
//	}

	@Override
	public Response getUserInfo() throws RuntimeException {
		
		String idToken = SCADSecurityManager.getIDTOKEN();
		
		if (idToken == null) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Missing SCAD idToken").build();
		}

		JsonParser parser = new JsonParser();
		String[] splitString = idToken.split("\\.");
		String header = new String(Base64.getDecoder().decode(splitString[0]));
		JsonObject headerJson = parser.parse(header).getAsJsonObject();
		String kId = headerJson.get("kid").getAsString();
		String alg = headerJson.get("alg").getAsString();


		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://api.login.yahoo.com/openid/v1/certs");
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String value = response.readEntity(String.class);

		JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
		JsonElement yahooPublicKey = null;
		for (JsonElement element : jsonObject.get("keys").getAsJsonArray()) {
			if (element.getAsJsonObject().get("kid").getAsString().equals(kId) && element.getAsJsonObject().get("alg").getAsString().equals(alg)) {
				yahooPublicKey = element;
			}
		}


		String x = yahooPublicKey.getAsJsonObject().get("x").getAsString();
		String y = yahooPublicKey.getAsJsonObject().get("y").getAsString();

		Base64URL x64 = new Base64URL(x);
		Base64URL y64 = new Base64URL(y);

		try {
			ECKey key = new ECKey.Builder(Curve.P_256, x64, y64).algorithm(JWSAlgorithm.ES256).keyID(kId).build();
			JWSVerifier verifier = new ECDSAVerifier(key);
			SignedJWT signedJWT = SignedJWT.parse(idToken);
			boolean verified = signedJWT.verify(verifier);
			if (!verified) {
				return Response.status(Response.Status.UNAUTHORIZED).entity("Your SCAD id token's signature could not be verified.").build();
			}
//			URL url = new URL("https", "api.login.yahoo.com", "/openid/v1/certs");
//			JwkProvider provider = new UrlJwkProvider(url);
//			Jwk jwk = provider.get(kId);
//			Algorithm algo = new Algorithm("ES256");
//			ECKeyGenerator generator = new ECKeyGenerator(Curve.P_256);
//			KeyStore keyStore = new
//			ECKey ecJWK = new ECKeyGenerator(Curve.P_256).keyID(kId).algorithm(algo).generate();
//			ECKey ecPublicKey = ecJWK.toPublicJWK();


//			ECPoint ecPoint = new ECPoint(new BigInteger(x), new BigInteger(y));
//			ECParameterSpec ecParameterSpec = new ECParameterSpec()
//			KeySpec keySpec = new ECPublicKeySpec(ecPoint);
//			KeyFactory kf = KeyFactory.getInstance("EC");

//
//			PublicKey publicKey = jwk.getPublicKey();
//			Jws claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(ID_TOKEN);

//			Algorithm algorithm = Algorithm.ECDSA256(publicKey, null);
//			JWTVerifier verifier = JWT.require(algorithm)
//									  .withIssuer()
//									  .build();
//			DecodedJWT jwt = verifier.verify(ID_TOKEN);
		} catch (Exception ex) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("There was a problem parsing your SCAD id token.").build();
		}

		String payload = new String(Base64.getDecoder().decode(splitString[1]));
		JsonObject jsonPayload = parser.parse(payload).getAsJsonObject();

		UserDto result = new UserDto();
		result.setAtHash(jsonPayload.get("at_hash").getAsString());
		result.setSub(jsonPayload.get("sub").getAsString());
		result.setEmailVerified(jsonPayload.get("email_verified").getAsBoolean());
		result.setBirthDate(jsonPayload.get("birthdate").getAsString());
		result.setIss(jsonPayload.get("iss").getAsString());
		Map<String, String> profImages = new HashMap<>();
		for (String key : new String[]{"image64", "image192", "image128", "image32"}) {
			JsonElement imageUrl = jsonPayload.get("profile_images").getAsJsonObject().get(key);
			if (imageUrl != null) {
				profImages.put(key, imageUrl.getAsString());
			}
		}
		result.setProfileImages(profImages);
		result.setGivenName(jsonPayload.get("given_name").getAsString());
		result.setLocale(jsonPayload.get("locale").getAsString());
		result.setNonce(jsonPayload.get("nonce").getAsString());
		result.setPicture(jsonPayload.get("picture").getAsString());
		result.setAud(jsonPayload.get("aud").getAsString());
		result.setAuthTime(jsonPayload.get("auth_time").getAsInt());
		result.setNickName(jsonPayload.get("nickname").getAsString());
		result.setName(jsonPayload.get("name").getAsString());
		result.setSessionExp(jsonPayload.get("session_exp").getAsInt());
		result.setExp(jsonPayload.get("exp").getAsInt());
		result.setIat(jsonPayload.get("iat").getAsInt());
		result.setFamilyName(jsonPayload.get("family_name").getAsString());
		result.setEmail(jsonPayload.get("email").getAsString());
		return Response.status(Response.Status.OK).entity(result).build();
		
	}

}
