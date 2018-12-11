package com.dotcom.social.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.dotcom.social.model.Usuario;

@Service
public class LinkedInService {

	@Value("${spring.social.linkedin.appId}")
	String linkedinAppId;
	@Value("${spring.social.linkedin.appSecret}")
	String linkedinSecret;

	public String createLinkedInAuthorURL(String scope) {
		System.out.println("LinkedInService.createLinkedInAuthorURL()");
		LinkedInConnectionFactory connectionFactory = new LinkedInConnectionFactory(linkedinAppId, linkedinSecret);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri("http://localhost:8080/linkedin");
		params.setScope(scope);
		String url = oauthOperations.buildAuthorizeUrl(params);
		System.out.println("  -->"+url);
		return url;
	}

	public String createLinkedInAccessToken(String code) {
		System.out.println("LinkedInService.createLinkedInAccessToken()");
		LinkedInConnectionFactory connectionFactory = new LinkedInConnectionFactory(linkedinAppId, linkedinSecret);
		AccessGrant accessGrant = connectionFactory
			.getOAuthOperations()
		  .exchangeForAccess(code, "http://localhost:8080/linkedin", null);
		String accessToken = accessGrant.getAccessToken();
		System.out.println("  -->"+accessToken);
		return accessToken;
	}

	public Usuario getUser(String accessToken) {
		System.out.println("LinkedInService.getUser()");
		Usuario usuario = new Usuario();
		String raw = "";
		try {
			LinkedIn linkedin = new LinkedInTemplate(accessToken);
      
			LinkedInProfile profile = linkedin.profileOperations().getUserProfile();
			
			usuario.setId(profile.getId());
			usuario.setName(profile.getFirstName() + " " + profile.getLastName());
			usuario.setFirstName(profile.getFirstName());
			usuario.setLastName(profile.getLastName());
			usuario.setEmail(profile.getEmailAddress());
			usuario.setFoto(profile.getProfilePictureUrl());
			usuario.setAccessToken(accessToken);
			usuario.setSocialLogin("LINKEDIN");
			
			System.out.println("FIM");
		}catch(Exception e) {
			System.out.println("Msg Erro  :"+e.getMessage());
			System.out.println("Saida Json:"+raw);
		}
		return usuario;
	}

}
