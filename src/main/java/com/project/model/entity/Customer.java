package com.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private Long id;

    private String fistName;

    private String lastName;

    @Min(value = 0, message = "The salary cannot be less than 0")
    @Max(value = 1000000, message = "The salary cannot be greater than 1000000")
    private double salary;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<CreditApplicationResult> creditApplicationResults = new ArrayList<>();

    public Customer(Long id, String fistName, String lastName, double salary, String phoneNumber) {
        this.id = id;
        this.fistName = fistName;
        this.lastName = lastName;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
    }
}
