package kh.edu.istasd.fswdapi.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String amount;

    private String remark;


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "resiver_id")
    private Account receiver;

    @ManyToOne
    @JoinColumn(name = "tranType_id")
    private TransactionType transactionType;


}
