package com.dailycodebuffer.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodebuffer.user.VO.Department;
import com.dailycodebuffer.user.VO.ResponseTemplateVO;
import com.dailycodebuffer.user.entity.User;
import com.dailycodebuffer.user.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static final String USER_SERVICE= "userService";
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public User saveUser(@RequestBody User user) {
		logger.info("inside saveUser method of UserController ");
		return userService.saveUser(user);
		
	}
	
	@GetMapping("/{id}")
	@CircuitBreaker(name = USER_SERVICE ,fallbackMethod = "userFallBack")
	public ResponseTemplateVO getUserWithDepatment(@PathVariable("id") Long userid) {
		return userService.getUserWithDepatment(userid);
		
	}
	
	
	public ResponseTemplateVO userFallBack(Exception e) {
		User u= new User();
		u.setDepartmentId("101");
		u.setEmail("shiv");
		u.setFirstName("shiv");
		u.setLastName("kumar");
		u.setUserId(new Long(3433));
		
		return new ResponseTemplateVO(u,new Department());
	}


}
