package com.dotcom.social.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.dotcom.social.model.Usuario;
import com.dotcom.social.model.User;

@Service
public class FacebookService {

	@Value("${spring.social.facebook.appId}")
	String facebookAppId;
	@Value("${spring.social.facebook.appSecret}")
	String facebookSecret;

	public String createFacebookAuthorURL(String scope) {
		System.out.println("FacebookService.createFBAuthorURL()");
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri("http://localhost:8080/facebook");
		params.setScope(scope);
		String url = oauthOperations.buildAuthorizeUrl(params);
		System.out.println("  -->"+url);
		return url;
	}

	public String createFacebookAccessToken(String code) {
		System.out.println("FacebookService.createFacebookAccessToken()");
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
		AccessGrant accessGrant = connectionFactory
			.getOAuthOperations()
		  .exchangeForAccess(code, "http://localhost:8080/facebook", null);
		String accessToken = accessGrant.getAccessToken();
		System.out.println("  -->"+accessToken);
		return accessToken;
	}

	public Usuario getUser(String accessToken) {
		System.out.println("FacebookService.getUser()");
		Usuario usuario = new Usuario();
		String json = "";
		try {
			Facebook facebook = new FacebookTemplate(accessToken);
			String [] fields = { "id", "email", "first_name", "last_name", "name", "picture"};
			
			User user = facebook.fetchObject("me", User.class, fields);
			json = facebook.fetchObject("me", String.class, fields);
			
			usuario.setId(user.getId());
		  usuario.setFirstName(user.getFirstName());	
			usuario.setLastName(user.getLastName()); 
			usuario.setName(user.getName()); 
			usuario.setEmail(user.getEmail());
			usuario.setAccessToken(accessToken);
			usuario.setSocialLogin("FACEBOOK");
			usuario.setFoto(user.getPicture().getData().getUrl());
			
			System.out.println("FIM");
			
		}catch(Exception e) {
			System.out.println("Msg Erro:"+e.toString());
			System.out.println("Json    :" + json);
		}
		return usuario;
	}

}
