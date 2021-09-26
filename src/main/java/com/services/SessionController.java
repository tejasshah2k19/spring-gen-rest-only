package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.UserDto;
import com.entity.UserEntity;
import com.repository.UserRepository;
import com.util.TokenUtil;

@RestController
public class SessionController {

	@Autowired
	UserRepository userRepo;

	@PostMapping("/signup")
	public ResponseEntity<UserEntity> signup(UserEntity user) {
		userRepo.save(user);
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(UserDto user) {

		UserEntity currentUser = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
		System.out.println(currentUser);
		if (currentUser == null) {
			return new ResponseEntity<String>("{\"error\":\"invalid\"}", HttpStatus.FORBIDDEN);
		} else {
			String authToken = TokenUtil.generateToken(); 
			currentUser.setAuthToken(authToken);
			userRepo.save(currentUser);// update 
			return new ResponseEntity<Object>(currentUser, HttpStatus.OK);

		}
	}

}
