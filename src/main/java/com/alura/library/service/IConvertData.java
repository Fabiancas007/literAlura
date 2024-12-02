package com.alura.library.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> classs);
}
