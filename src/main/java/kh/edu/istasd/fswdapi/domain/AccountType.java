package kh.edu.istasd.fswdapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accountType")
@Setter
@Getter
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true,length = 32 )
    private String name;

    @Column(length = 255,columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "accountType")
    private List<Account> account; //one only saving can have only one account
}
