package com.splitbill.splitbill.Repository;

import com.splitbill.splitbill.Entity.Expense;
import com.splitbill.splitbill.Entity.ExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, UUID> {

    List<ExpenseShare> findByExpenseId(UUID expenseId);
}
