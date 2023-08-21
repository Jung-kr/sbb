package com.mygroup.sbb.chatgpt;

import com.mygroup.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

@Service
@RequiredArgsConstructor
public class ChatGptMessageService {
    private static final String TOPIC = "chatgpt";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Question question) {

        JSONObject msgData = new JSONObject();
        msgData.put("id", question.getId());
        msgData.put("title", question.getSubject());
        msgData.put("content",question.getContent());

        JSONObject msgObj = new JSONObject();
        msgObj.put("command", "request_chatgpt");
        msgObj.put("data", msgData);

        String msg = msgObj.toString(4);
        kafkaTemplate.send(TOPIC, msg);
    }
}