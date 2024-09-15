package com.sadikov.loginapp.service;

import com.sadikov.loginapp.dto.UserCreateDTO;
import com.sadikov.loginapp.exceptions.KeycloakException;
import com.sadikov.loginapp.exceptions.UserNotCreatedException;
import com.sadikov.loginapp.exceptions.UserNotFoundException;
import com.sadikov.loginapp.mapper.Mapper;
import com.sadikov.loginapp.model.Users;
import com.sadikov.loginapp.repository.UserRepository;
import lombok.Data;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService {
    @Autowired
    private KeycloakService keycloakService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;

    public void createNewUser(UserCreateDTO userCreateDTO) throws UserNotFoundException, UserNotCreatedException {
        UserRepresentation userRepresentation = Mapper.convertToUserRepresentation(userCreateDTO);
        try {
            keycloakService.addUser(userRepresentation);
            addUserDataBase(userRepresentation);
        } catch (KeycloakException e) {
            throw new UserNotCreatedException(e.getMessage(),
                    (HttpStatus) HttpStatusCode.valueOf(e.getResponse().getStatus()));
        }
    }
    private void addUserDataBase(UserRepresentation userRepresentation) throws UserNotFoundException, UserNotCreatedException {
        Users users = new Users();
//        users.setId(keycloakService.getUserIdByUserName(userRepresentation.getUsername()));
        users.setUserName(userRepresentation.getUsername());
        users.setFirstName(userRepresentation.getFirstName());
        users.setLastName(userRepresentation.getLastName());
        users.setEmail(userRepresentation.getEmail());
        userRepository.save(users);
    }
}
