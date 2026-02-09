package com.toy.springaidemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        // 시스템 메시지 기본 설정 (AI의 페르소나 설정)
        this.chatClient = builder
                .defaultSystem("당신은 신비롭고 통찰력 있는 전문 점성술사입니다. 사용자의 생년월일시를 바탕으로 별자리(Sun Sign), 상승궁(Ascendant)을 분석하고 성격과 올해의 운세를 친절하고 신비로운 어조로 설명해주세요.")
                .build();
    }

    @GetMapping("/")
    public String home() {
        return "fortune"; // 템플릿 파일명: fortune.html
    }

    @PostMapping("/api/fortune")
    @ResponseBody
    public Map<String, String> analyzeFortune(@RequestBody FortuneRequest request) {
        // 프롬프트 구성
        String userMessage = String.format(
                "내 생일은 %s이고, 태어난 시간은 %s야. 나의 점성술 운세를 봐줘.",
                request.birthDate(), request.birthTime()
        );

        String response = chatClient.prompt()
                .user(userMessage)
                .call()
                .content();

        return Map.of("response", response);
    }

    // DTO (Java 16+ Record 사용)
    public record FortuneRequest(String birthDate, String birthTime) {}
}
