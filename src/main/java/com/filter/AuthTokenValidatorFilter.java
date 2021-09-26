package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.entity.UserEntity;
import com.google.gson.Gson;
import com.repository.UserRepository;

@Component
public class AuthTokenValidatorFilter implements Filter {

	@Autowired
	UserRepository userRepo;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("AuthTokenFilter Called....");
		// signup
		// authenticate
		// ignore

		// api/users
		// api/**

		boolean isValidUser = false;
		if (((HttpServletRequest) request).getRequestURL().toString().contains("/api/")) {
			// token verify
			String authToken = ((HttpServletRequest) request).getHeader("authToken");
			System.out.println("authToken => " + authToken);
			UserEntity user = userRepo.findByAuthToken(authToken);
			if (user != null) {
				isValidUser = true;
			}

		} else {
			// ignore token
			isValidUser = true;
		}

		if (isValidUser) {
			chain.doFilter(request, response);

		} else {
			// go back

			ResponseEntity<String> resp = new ResponseEntity<String>("Invalid user Please Login", HttpStatus.FORBIDDEN);
			response.setContentType("application/json");
			//
			String data = new Gson().toJson(resp);
			response.getWriter().print(data);
			
			System.out.println("invalid token...");
		}
	}

}
