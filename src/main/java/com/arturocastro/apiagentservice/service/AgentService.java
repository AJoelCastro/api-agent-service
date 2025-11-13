package com.arturocastro.apiagentservice.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonValue;
import com.openai.models.beta.chatkit.ChatKitWorkflow;
import com.openai.models.beta.chatkit.sessions.SessionCreateParams;
import com.openai.models.beta.chatkit.threads.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AgentService {

    private final OpenAIClient client;

    public AgentService(@Value("${openai.api.key}") String apiKey){
        client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public ChatSession agentBuilder(){

        Map<String, JsonValue> additionalProperties = Map.of(
                "input_as_text", JsonValue.from("Quiero crear un negocio de tecnologia")
        );

        ChatSessionWorkflowParam chatSessionWorkflowParam = ChatSessionWorkflowParam.builder()
                .id("wf_6915055d87ec819094e860f95237ac3701c8ecc6713ba46d")
                .stateVariables(
                        ChatSessionWorkflowParam.StateVariables.builder()
                                .additionalProperties(additionalProperties)
                                .build()
                )
                .version("1")
                .build();

        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                .user("Arturo Castro")
                .workflow(chatSessionWorkflowParam)
                .expiresAfter(
                        ChatSessionExpiresAfterParam.builder()
                                .seconds(300)
                                .build()
                )
                .build();

        return client.beta().chatkit().sessions().create(sessionCreateParams);

    }

}
