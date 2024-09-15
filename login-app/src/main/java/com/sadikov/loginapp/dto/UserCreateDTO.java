package com.sadikov.loginapp.dto;

import lombok.Data;

@Data
public class UserCreateDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
