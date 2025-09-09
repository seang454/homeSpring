package kh.edu.istasd.fswdapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "chat")
@Getter
@Setter
@ToString
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String senderId;   // store sender username or id

    @Column(nullable = false)
    private String receiverId; // store receiver username or id
}
