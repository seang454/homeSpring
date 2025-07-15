package kh.edu.istasd.fswdapi.domain;

import jakarta.persistence.*;
import lombok.*;

//know your customer : is to verify that you are identified as customer
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Setter
@Getter
@Table(name = "kyc")
public class KYC {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; // uuid
    private String nationalCardId;

    private Boolean isVerified;
    private Boolean isDeleted;

     //implement using reference ket
//    @OneToOne(mappedBy = "kyc")
//    private Customer customer;

    //implement shared primary key
    @OneToOne
    @MapsId   //Mapping id of customer to id of KYC above
    @JoinColumn(name = "cust_id")
    private Customer customer;

}
