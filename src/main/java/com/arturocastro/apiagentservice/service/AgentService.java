package com.arturocastro.apiagentservice.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonField;
import com.openai.core.JsonValue;
import com.openai.models.ChatModel;
import com.openai.models.beta.assistants.Assistant;
import com.openai.models.beta.assistants.AssistantCreateParams;
import com.openai.models.beta.chatkit.ChatKitWorkflow;
import com.openai.models.beta.chatkit.sessions.SessionCreateParams;
import com.openai.models.beta.chatkit.threads.*;
import com.openai.models.beta.threads.ThreadCreateAndRunParams;
import com.openai.models.beta.threads.ThreadCreateParams;
import com.openai.models.beta.threads.runs.Run;
import com.openai.models.beta.threads.runs.RunCreateParams;
import com.openai.services.blocking.beta.chatkit.SessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ObjectInputFilter;
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
                .chatkitConfiguration(
                        ChatSessionChatKitConfigurationParam.builder()
                                .automaticThreadTitling(
                                        ChatSessionChatKitConfigurationParam.AutomaticThreadTitling.builder()
                                                .enabled(true)
                                                .build()
                                )
                                .build()
                )
                .expiresAfter(
                        ChatSessionExpiresAfterParam.builder()
                                .seconds(300)
                                .build()
                )
                .build();

        ChatSession chatSession = client.beta().chatkit().sessions().create(sessionCreateParams);

        System.out.println("chatSession: " + chatSession);

        AssistantCreateParams assistantCreateParams = AssistantCreateParams.builder()
                .model(ChatModel.GPT_4O)
                .instructions("Eres un excelente innovador que ha creado las mas grandes empresas del mundo")
                .build();

        Assistant assistant = client.beta().assistants().create(assistantCreateParams);

        System.out.println("assistant: " + assistant);

        ThreadCreateParams threadCreateParams = ThreadCreateParams.builder()
                .build();

        com.openai.models.beta.threads.Thread thread = client.beta().threads().create(threadCreateParams);

        System.out.println("thread: " + thread);

        RunCreateParams runCreateParams = RunCreateParams.builder()
                .threadId(thread.id())
                .assistantId(assistant.id())
                .build();

        Run run = client.beta().threads().runs().create(runCreateParams);

        System.out.println("run: " + run);

        ThreadRetrieveParams threadRetrieveParams = ThreadRetrieveParams.builder()
                .threadId(run.threadId())
                .build();

        ChatKitThread chatKitThread = client.beta().chatkit().threads().retrieve(threadRetrieveParams);

        System.out.println("chatKitThread: " + chatKitThread);


        return client.beta().chatkit().sessions().create(sessionCreateParams);

    }

}
