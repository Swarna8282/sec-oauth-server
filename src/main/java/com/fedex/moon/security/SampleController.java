package com.fedex.moon.security;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	
	@RequestMapping ("/userinfo")
	public Principal user (Principal pal) {
		System.out.println(pal);
		return pal;
	}

}
