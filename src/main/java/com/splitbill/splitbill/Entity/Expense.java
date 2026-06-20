package com.splitbill.splitbill.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="expenses")
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name="bill_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "paidBy")
    private Participant paidBy;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL,orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<ExpenseShare> shares=new ArrayList<>();
}
