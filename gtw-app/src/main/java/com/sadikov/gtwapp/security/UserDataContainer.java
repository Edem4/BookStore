package com.sadikov.gtwapp.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface UserDataContainer extends UserDetails {
    Set<String> getRoles();
    String getUserId();
    String getEmail();
}