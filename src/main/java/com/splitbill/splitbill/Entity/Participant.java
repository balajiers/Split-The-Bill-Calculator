package com.splitbill.splitbill.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="participants")
@Data
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String upiId;

    @ManyToOne
    @JoinColumn(name="bill_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Bill bill;


    @OneToMany(mappedBy = "paidBy", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Expense> expensesPaid=new ArrayList<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<ExpenseShare> shares=new ArrayList<>();
}
