package com.dotcom.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dotcom.social.model.Usuario;
import com.dotcom.social.model.FormLogin;
import com.dotcom.social.service.FacebookService;
import com.dotcom.social.service.GoogleService;
import com.dotcom.social.service.LinkedInService;

@Controller
@CrossOrigin(origins="*")
public class AllController {

	@Autowired
	GoogleService googleService;
	@Autowired
	FacebookService facebookService;
	@Autowired
	LinkedInService linkedInService;
	
	Usuario usuario;
	ModelAndView mv;
	
	@GetMapping("/")
	public String raiz() {
		System.out.println("/");
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		System.out.println("/login");
		return "login";
	}

	@PostMapping("/loginSocial")
	public String loginSocial(FormLogin formLogin) {
		System.out.println("/loginSocial = "+formLogin.getSocial());
		String urlAuthor;
    if ( formLogin.getSocial().equalsIgnoreCase("facebook") ) {
    	urlAuthor = facebookService.createFacebookAuthorURL(formLogin.getScope());
    }else if ( formLogin.getSocial().equalsIgnoreCase("google") ) {
    	urlAuthor = googleService.createGoogleAuthorURL(formLogin.getScope());
    }else if ( formLogin.getSocial().equalsIgnoreCase("linkedin") ) {
    	urlAuthor = linkedInService.createLinkedInAuthorURL(formLogin.getScope());
    } else {
    	urlAuthor = "login";
    }
    return "redirect:"+urlAuthor;
	}
	
	@GetMapping("/facebook")
	public ModelAndView createFacebookAccessToken(@RequestParam("code") String code) {
		System.out.println("/facebook");
		String accessToken = facebookService.createFacebookAccessToken(code);
		Usuario usuario = facebookService.getUser(accessToken);
		mv = new ModelAndView("principal/inicio");
		mv.addObject("usuario",usuario);
		return mv;
	}
	
	@GetMapping("/google")
	public ModelAndView createGoogleAccessToken(@RequestParam("code") String code) {
		System.out.println("/google");
		String accessToken = googleService.createGoogleAccessToken(code);
		Usuario usuario = googleService.getUser(accessToken);
		mv = new ModelAndView("principal/inicio");
		mv.addObject("usuario",usuario);
		return mv;
	}

	@GetMapping("/linkedin")
	public ModelAndView createLinkedInAccessToken(@RequestParam("code") String code) {
		System.out.println("/linkedin");
		String accessToken = linkedInService.createLinkedInAccessToken(code);
		Usuario usuario = linkedInService.getUser(accessToken);
		mv = new ModelAndView("principal/inicio");
		mv.addObject("usuario",usuario);
		return mv;
	}
	
	@GetMapping("/principal/inicio")
	public ModelAndView inicio(Usuario usuario) {
		System.out.println("/principal/inicio");
		mv = new ModelAndView("/principal/inicio");
		mv.addObject("usuario",usuario);
		return mv;
	}
	
}
