package com.example.demo.controller;



import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;

@RestController
@RequestMapping(path = "/users")
public class UserController {
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping
	public ResponseEntity<User> addUser( @RequestBody User user) {
		User savedUser  = userRepo.save(user);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getUserId()).toUri();  
		return ResponseEntity.created(location).build();  
	}
	
	@GetMapping("{userId}")
	public ResponseEntity<User> findUser(@PathVariable int userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> 
		new RuntimeException("User not found with following id"));
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable int userId,  @RequestBody User user) {
		User fetchedUser = userRepo.findById(userId).orElseThrow(() -> 
		new RuntimeException("User not found with following id"));
		fetchedUser.setUserName(user.getUserName());
		userRepo.save(fetchedUser);
		return ResponseEntity.ok(fetchedUser);
	}
	
	@GetMapping
	public List<User> findAllUsers() {
		List<User> user = userRepo.findAll();
		return user;
	}
	
        
    @DeleteMapping(value="/{userId}")    
    public void delete(@PathVariable int userId){    
        userRepo.delete(userRepo.getById(userId)); 
    }    
    
}
