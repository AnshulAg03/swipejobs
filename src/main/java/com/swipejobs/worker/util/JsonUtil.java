package com.swipejobs.worker.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class JsonUtil {


    public static <T> T readJson(String fileName, Class<T> clazz) throws IOException {
        InputStream url = JsonUtil.class.getClassLoader().getResourceAsStream(fileName);
        return new ObjectMapper().readValue(url,clazz);
    }

    public static <T> List<T> jsonArrayToList(String fileName, Class<T> clazz) throws IOException {
        InputStream url = JsonUtil.class.getClassLoader().getResourceAsStream(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        return objectMapper.readValue(url, collectionType);
    }
}
