package com.sadikov.loginapp.mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sadikov.loginapp.dto.UserCreateDTO;
import com.sadikov.loginapp.model.UserHeader;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class Mapper {
    @Autowired
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private static final CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class,String.class);

    public static UserHeader getUserFromHeaders(HttpHeaders headers) throws JsonProcessingException {
        UserHeader user = new UserHeader();
        user.setUserId(headers.get("userId").get(0));
        user.setUserName(headers.get("userName").get(0));
        user.setEmail(headers.get("userEmail").get(0));
        user.setRoles(objectMapper.readValue(headers.get("roles").get(0), collectionType));
        return user;
    }


    public static UserRepresentation convertToUserRepresentation(UserCreateDTO userCreateDTO){

        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(userCreateDTO.getEmail());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setUsername(userCreateDTO.getUserName());
        userRepresentation.setFirstName(userCreateDTO.getFirstName());
        userRepresentation.setLastName(userCreateDTO.getLastName());

        userRepresentation.setCredentials(Collections.singletonList(createCredential(userCreateDTO.getPassword())));

        return userRepresentation;
    }

    private static CredentialRepresentation createCredential(String password){
        CredentialRepresentation credentials = new CredentialRepresentation();

        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(password);
        credentials.setTemporary(false);

        return credentials;
    }
}
