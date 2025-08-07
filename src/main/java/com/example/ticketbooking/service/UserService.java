package com.example.ticketbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ticketbooking.Entities.User;

import com.example.ticketbooking.repository.UserRepository;

@Service
public class UserService {
@Autowired
UserRepository userRepository;
@Autowired
PasswordEncoder passwordEncoder;

public void saveNewUser(String userName, String password){
    if (userRepository.findByUserName(userName).isPresent()) {
       throw new RuntimeException("User already exists");
    }
    User user = new User();
    user.setUserName(userName);
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
}

public User getUserByUserName(String userName){
    return userRepository.findByUserName(userName).orElse(null);
}
}
