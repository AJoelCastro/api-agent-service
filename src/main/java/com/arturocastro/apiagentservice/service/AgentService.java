package com.arturocastro.apiagentservice.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    private final OpenAIClient client;

    public AgentService(@Value("${openai.api.key}") String apiKey){
        client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

}
