package com.bigeek.microservices.userservice.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bigeek.microservices.userservice.model.User;
import com.bigeek.microservices.userservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserService {
	
	final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;
	
	public List<User> findAll() {
		logger.info("UserService findAll");
		return userRepository.findAll();
	}

	public User save(User user) {
		logger.info("UserService save user: {}", user.toString());
		return userRepository.save(user);
	}

	public Optional<User> findOne(int id) {
		logger.info("UserService findOne user with id: {}", id);
		return userRepository.findById(id);
	}

	public void deleteById(int id) {
		logger.info("UserService delete user with id: {}", id);
		userRepository.deleteById(id);
	}

}
