package kh.edu.istasd.fswdapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customers")
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false,unique = true)
    private String nationalCardId;

    @Column(length = 15,nullable = false)
    private String gender;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @Column( columnDefinition = "TEXT")
    private String remark;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "segment_id")
    private CustomerSegment segment;


    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonIgnore                     //customer refer to 'customer' name in Account
                                    // one customer can have many Account
                                    // mappedBy is used to tell Hibernate that the relationship is already define in another side
    private List<Account> accounts;

      //using reference key
//    @OneToOne
//    @JoinColumn(unique = true)
//    private KYC kyc; //create kyc_id

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn  //mean using the primarykey of Customer to Kyc
    private KYC kyc;


}
