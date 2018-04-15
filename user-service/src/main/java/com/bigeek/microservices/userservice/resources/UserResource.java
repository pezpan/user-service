package com.bigeek.microservices.userservice.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bigeek.microservices.userservice.exception.UserNotFoundException;
import com.bigeek.microservices.userservice.model.User;
import com.bigeek.microservices.userservice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserResource {
	
	final Logger logger = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		logger.info("GET /users");
		return userService.findAll();
	}

	@GetMapping("/users/{id}")
	public Resource retrieveUser(@PathVariable int id) {
		logger.info("GET /users/{}", id);
		Optional<User> user = userService.findOne(id);
		// Verificamos
		if(!user.isPresent()){
			logger.error("User not found");
			throw new UserNotFoundException("id-"+ id);
		}
		// HATEOAS todos los usuarios
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		logger.info("DELETE /users/{}", id);
		userService.deleteById(id);		
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		logger.info("POST /users with request: {}", user.toString());
		User savedUser = userService.save(user);
		// Usuario creado. Devolvemos uri del usuario creado
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}
