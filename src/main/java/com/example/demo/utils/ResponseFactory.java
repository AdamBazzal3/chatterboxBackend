package com.example.demo.utils;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseFactory {
    private final Map<Boolean, APIResponse> responseCache = new HashMap<>();

    public APIResponse getResponse(boolean key) {

        if (responseCache.containsKey(key)) {
            return responseCache.get(key);
        } else {

            APIResponse response;
            response = createResponse(key);

            responseCache.put(key, response);

            return response;
        }
    }

    private APIResponse createResponse(boolean responseIsSuccess) {

        return new APIResponse(responseIsSuccess);
    }
}
