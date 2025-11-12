package com.arturocastro.apiagentservice.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.beta.chatkit.ChatKitWorkflow;
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

    public void agentBuilder(){
        ChatKitWorkflow chatKitWorkflow = ChatKitWorkflow.builder()
                .id("wf_6915055d87ec819094e860f95237ac3701c8ecc6713ba46d")
                .stateVariables(
                        ChatKitWorkflow.StateVariables.builder()
                                .build()
                )
                .tracing(
                        ChatKitWorkflow.Tracing.builder()
                                .enabled(true)
                                .build()
                )
                .version("1")
                .build();
        System.out.println(chatKitWorkflow);
    }

}
