package kh.edu.istasd.fswdapi.repository;

import kh.edu.istasd.fswdapi.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(
            String senderId1, String receiverId1,
            String senderId2, String receiverId2
    );

}