package com.swipejobs.worker.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> jsonArrayToList(String fileName, Class<T> clazz) throws IOException {
        InputStream url = JsonUtil.class.getClassLoader().getResourceAsStream(fileName);
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        return objectMapper.readValue(url, collectionType);
    }

}
