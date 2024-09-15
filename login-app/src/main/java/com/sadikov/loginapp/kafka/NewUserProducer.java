package com.sadikov.loginapp.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadikov.loginapp.dto.UserCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NewUserProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public NewUserProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public void userMessage(UserCreateDTO userCreateDTO) throws JsonProcessingException {
        kafkaTemplate.send("newUser", objectMapper.writeValueAsString(userCreateDTO));
    }
//
//    public void pointMessage(PointDTO pointDTO) throws JsonProcessingException {
//        kafkaTemplate.send("point", objectMapper.writeValueAsString(pointDTO));
//    }
}
