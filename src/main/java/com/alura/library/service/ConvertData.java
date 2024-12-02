package com.alura.library.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> classs){
        try{
            return mapper.readValue(json.toString(), classs);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}