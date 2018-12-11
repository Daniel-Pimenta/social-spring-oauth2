package com.dotcom.social.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.dotcom.social.model.Usuario;

@Service
public class GoogleService {

	@Value("${spring.social.google.appId}")
	String googleAppId;
	@Value("${spring.social.google.appSecret}")
	String googleSecret;
	
	public String createGoogleAuthorURL(String scope) {
		System.out.println("GoogleService.createGAuthorURL()");
		GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(googleAppId, googleSecret);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri("http://localhost:8080/google");
		params.setScope(scope);
		String url = oauthOperations.buildAuthorizeUrl(params);
		System.out.println("  -->"+url);
		return url;
	}
	
	public String createGoogleAccessToken(String code) {
		System.out.println("GoogleService.createGoogleAccessToken()");
		GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(googleAppId, googleSecret);
		AccessGrant accessGrant = connectionFactory
			.getOAuthOperations()
		  .exchangeForAccess(code, "http://localhost:8080/google", null);
		String accessToken = accessGrant.getAccessToken();
		System.out.println("  -->"+accessToken);
		return accessToken;
	}
	
	public Usuario getUser(String accessToken) {
		System.out.println("GoogleService.getUser()");
		Usuario usuario = new Usuario();
		try {
			Google google = new GoogleTemplate(accessToken);
			Person person = google  .plusOperations().getGoogleProfile();
      usuario.setId(person.getId() );
      usuario.setFirstName(person.getGivenName());
      usuario.setLastName(person.getFamilyName());
      usuario.setName(person.getGivenName() + " " + person.getFamilyName());
      usuario.setEmail(person.getAccountEmail());
      usuario.setFoto(person.getImageUrl());
			usuario.setAccessToken(accessToken);
			usuario.setSocialLogin("GOOGLE");
			System.out.println("FIM");
		}catch(Exception e) {
			System.out.println("Erro:"+e.toString());
		}
		return usuario;
	}
	
}
