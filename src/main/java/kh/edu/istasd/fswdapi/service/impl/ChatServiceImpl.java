package kh.edu.istasd.fswdapi.service.impl;

import kh.edu.istasd.fswdapi.domain.ChatMessage;
import kh.edu.istasd.fswdapi.repository.ChatRepository;
import kh.edu.istasd.fswdapi.service.ChatServer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatServer {
    private final ChatRepository chatRepository;

    @Override
    public void saveChatMessage(ChatMessage chatMessage) {
        if (chatMessage != null) {
            chatRepository.save(chatMessage);
        }
    }
    @Override
    public List<ChatMessage> getChatMessage(String currentUserId, String selectedUserId) {
        if (selectedUserId != null && currentUserId != null) {
//            List<ChatMessage> chatMessage = chatRepository.getChatMessageBySenderIdAndReceiverId(currentUserId, selectedUserId);
            List<ChatMessage> chatMessage = chatRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(currentUserId,selectedUserId,selectedUserId,currentUserId);
            chatMessage.forEach(System.out::println);
            return chatMessage;
        }
        return null;
    }
}
