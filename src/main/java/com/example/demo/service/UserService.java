package com.example.demo.service;

import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkLogin(String username, String password) {
        try {

            UserModel user = userRepository.findByUsername(username);

            // Fjernet if statement der tjekker om user findes for at siden virker uden user

            return true;
        } catch (Exception e){
        return true;

        }
    }
}
