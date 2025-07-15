package kh.edu.istasd.fswdapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "customer_segements")
public class CustomerSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerSegmentId;

    @NotBlank(message = "segment name is required")
    private String segmentName;
    
    private Integer benefit;
    private boolean isDeleted;

    @OneToMany(mappedBy = "segment")
    private List<Customer >customer;

    @OneToMany(mappedBy = "segment")
    private List<Account > accounts;
}
