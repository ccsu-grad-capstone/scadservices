package edu.ccsu.cs595.capstone.scadservices.dto;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UserDto extends AuditedDto {
	private String atHash;
	private String sub;
	private Boolean emailVerified;
	private String birthDate;
	private String iss;
	private Map<String, String> profileImages;
	private String givenName;
	private String locale;
	private String nonce;
	private String picture;
	private String aud;
	private Integer authTime;
	private String nickName;
	private String name;
	private Integer sessionExp;
	private Integer exp;
	private Integer iat;
	private String familyName;
	private String email;

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public void setId(Long id) {

	}

	public String getAtHash() {
		return atHash;
	}

	public void setAtHash(String atHash) {
		this.atHash = atHash;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public Map<String, String> getProfileImages() {
		return profileImages;
	}

	public void setProfileImages(Map<String, String> profileImages) {
		this.profileImages = profileImages;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getAud() {
		return aud;
	}

	public void setAud(String aud) {
		this.aud = aud;
	}

	public Integer getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Integer authTime) {
		this.authTime = authTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSessionExp() {
		return sessionExp;
	}

	public void setSessionExp(Integer sessionExp) {
		this.sessionExp = sessionExp;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getIat() {
		return iat;
	}

	public void setIat(Integer iat) {
		this.iat = iat;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
