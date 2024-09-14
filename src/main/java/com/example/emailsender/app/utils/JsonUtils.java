package com.example.emailsender.app.utils;

import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

public class JsonUtils {

    public static String convertMessagesToJson(List<CreateMessageOutputDto> messages) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(messages);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }
}
