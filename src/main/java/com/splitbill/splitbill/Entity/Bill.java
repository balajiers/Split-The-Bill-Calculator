package com.splitbill.splitbill.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="bills")
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String shareUrl;

    @OneToMany(mappedBy = "bill",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Participant> participants=new ArrayList<>();

    @OneToMany(mappedBy = "bill",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Expense> expenses=new ArrayList<>();
}
