package com.sadikov.usersapp.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadikov.usersapp.dto.UserCreateDTO;
import com.sadikov.usersapp.service.UserService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NewUserConsumer {
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public NewUserConsumer(ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @KafkaListener(topics = "newUser", groupId = "users-app")
    public void listen(ConsumerRecord<String, String> record){
        try {
            UserCreateDTO userCreateDTO = objectMapper.readValue(record.value(), UserCreateDTO.class);
            userService.createNewUser(userCreateDTO);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
