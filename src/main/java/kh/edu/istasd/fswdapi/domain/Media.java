package kh.edu.istasd.fswdapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "medias")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique=true, nullable=false,length=225)
    private String name;

    @Column(nullable=false)
    private String extension;

    @Column(nullable=false,length=100)
    private String mimeType;

    @Column(nullable=false)
    private Boolean isDeleted;


}
