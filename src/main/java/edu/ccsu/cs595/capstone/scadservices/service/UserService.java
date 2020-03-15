package edu.ccsu.cs595.capstone.scadservices.service;

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
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.SignedJWT;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.util.GUIDGenerator;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Stateless
public class UserService {
	

	@Inject
	GUIDGenerator gen;

	private Client restClient = ClientBuilder.newClient();

	private static final String REST_URI = "https://api.login.yahoo.com";

	public boolean verifyUser(String idToken) {

		JsonParser parser = new JsonParser();
		String[] splitString = idToken.split("\\.");
		String header = new String(Base64.getDecoder().decode(splitString[0]));
		JsonObject headerJson = parser.parse(header).getAsJsonObject();
		String kId = headerJson.get("kid").getAsString();
		String alg = headerJson.get("alg").getAsString();


		WebTarget target = restClient.target(REST_URI + "/openid/v1/certs");
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

		if (yahooPublicKey != null) {
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
					return false;
				}
			} catch (Exception ex) {
				return false;
			}
			return true;
		}

		return false;
	}

	public UserDto createUserDtoFromJwt(String idToken) {
		JsonParser parser = new JsonParser();
		String[] splitString = idToken.split("\\.");
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
		return result;
	}

}
