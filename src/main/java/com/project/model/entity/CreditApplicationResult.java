package com.project.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class CreditApplicationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean result;

    private Double creditLimit;

    private Date createdDate;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    //  select * from CreditApplicationResult where customer_id=13123123123

    public CreditApplicationResult() {
    }


}
