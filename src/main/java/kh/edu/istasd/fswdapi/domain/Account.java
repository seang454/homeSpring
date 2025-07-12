package kh.edu.istasd.fswdapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "accounts") // you can table name by change this name on @Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true,length = 32 )
    private String accountNumber;

    @ManyToOne  // many account have can name one AccountType
    @JoinColumn(name = "accountType_id")
    private AccountType accountType;

    @Column(nullable = false,length = 50)
    private String accountCurrency;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Boolean isDeleted;

    //take customer id(primary) as primary key of Account id
    @ManyToOne    //Many account is controlled by only one Customer
    @JoinColumn(name = "customer_id") //
                                  // name is used to custom column name of relation
                                  //referencedColumnName one customer field
    private Customer customer;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> receivedTransactions;




}
