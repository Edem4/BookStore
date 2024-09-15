package com.sadikov.usersapp.service;

import com.sadikov.usersapp.dto.UserCreateDTO;
import com.sadikov.usersapp.model.Users;
import com.sadikov.usersapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createNewUser(UserCreateDTO userCreateDTO) {
        Users user = new Users();
        user.setUserName(userCreateDTO.getUserName());
        user.setEmail(userCreateDTO.getEmail());
        user.setFirstName(userCreateDTO.getFirstName());
        user.setLastName(userCreateDTO.getLastName());
        userRepository.save(user);
    }

}
