package kh.edu.istasd.fswdapi.service;

import kh.edu.istasd.fswdapi.domain.ChatMessage;

import java.util.List;

public interface ChatServer {
    void saveChatMessage(ChatMessage chatMessage);
    List<ChatMessage> getChatMessage(String currentUserId, String selectedUserId);
}
