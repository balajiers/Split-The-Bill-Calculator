package com.splitbill.splitbill.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name="expense_shares")
@Data
public class ExpenseShare {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @ManyToOne
    @JoinColumn(name="expense_id")
    private Expense expense;

    @ManyToOne
    @JoinColumn(name="participant_id")
    private Participant participant;

}
