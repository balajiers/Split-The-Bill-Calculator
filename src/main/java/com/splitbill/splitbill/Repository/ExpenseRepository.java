package com.splitbill.splitbill.Repository;

import com.splitbill.splitbill.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByBillId(UUID billId);
}
