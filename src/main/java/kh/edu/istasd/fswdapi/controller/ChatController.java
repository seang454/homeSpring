package kh.edu.istasd.fswdapi.controller;

import kh.edu.istasd.fswdapi.domain.ChatMessage;
import kh.edu.istasd.fswdapi.service.ChatServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatServer chatServer;

    /**
     * Client publishes to /app/private-message
     * We fan out to two user-specific topics:
     *   /topic/user.{receiverId}
     *   /topic/user.{senderId}
     */
    @MessageMapping("/private-message")
    public void sendPrivateMessage(@Payload ChatMessage message) {
        log.info("Incoming message: {}", message);

        // Persist first (so IDs/timestamps are set if your service does that)
        chatServer.saveChatMessage(message);

        // Fan-out to receiver
        messagingTemplate.convertAndSend("/topic/user." + message.getReceiverId(), message);
        //you will send message twice when you add this
        // Fan-out to sender (to confirm delivery/update temp IDs, etc.)
//        messagingTemplate.convertAndSend("/topic/user." + message.getSenderId(), message);

        // Optional simple auto-reply example
        if (message.getMessage() != null && message.getMessage().equalsIgnoreCase("hi")) {
            ChatMessage autoReply = new ChatMessage();
            autoReply.setSenderId(message.getReceiverId());
            autoReply.setReceiverId(message.getSenderId());
            autoReply.setMessage("Hello! I got your message.");
            chatServer.saveChatMessage(autoReply);
            messagingTemplate.convertAndSend("/topic/user." + autoReply.getReceiverId(), autoReply);
        }
    }

    @GetMapping("/history/{currentUserId}/{selectedUserId}")
    public List<ChatMessage> getHistory(@PathVariable String currentUserId,
                                        @PathVariable String selectedUserId) {
        log.info("History request: {} <-> {}", currentUserId, selectedUserId);
        return chatServer.getChatMessage(currentUserId, selectedUserId);
    }
}
