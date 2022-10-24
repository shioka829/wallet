package com.example.wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
	@CrossOrigin
	public String getIndex() {
		return "index";
	}

    @GetMapping("/login")
	@CrossOrigin
	public String getLogin() {
		return "login";
	}

    @GetMapping("/signup")
	@CrossOrigin
	public String getSignup() {
		return "signup";
	}
	
	@GetMapping("/group")
	@CrossOrigin
	public String getGroup() {
		return "group";
	}

	@GetMapping("/input")
	@CrossOrigin
	public String getInput() {
		return "input";
	}
	
	@GetMapping("/detail")
	@CrossOrigin
	public String getDetail() {
		return "detail";
	}

	@GetMapping("/error")
	@CrossOrigin
	public String getError() {
		return "error";
	}
}
