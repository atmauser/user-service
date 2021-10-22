package com.dailycodebuffer.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dailycodebuffer.user.VO.Department;
import com.dailycodebuffer.user.VO.ResponseTemplateVO;
import com.dailycodebuffer.user.entity.User;
import com.dailycodebuffer.user.repository.UserRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.security.PublicKey;

import javax.management.loading.PrivateClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		logger.info("inside saveUser method of UserService ");
		return userRepository.save(user);
	}

	
	public ResponseTemplateVO getUserWithDepatment(Long userid) {
		logger.info("inside getUserWithDepatment method of UserService ");
		
	
		
		ResponseTemplateVO vo= new ResponseTemplateVO();
		User user= userRepository.findByUserId(userid);
		
		Department department = 
				restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/"+user.getDepartmentId(),Department.class );
		vo.setUser(user);
		vo.setDepartment(department);
		return vo;
	}


}
