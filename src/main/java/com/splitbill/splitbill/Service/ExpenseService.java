package com.splitbill.splitbill.Service;

import com.splitbill.splitbill.Entity.Bill;
import com.splitbill.splitbill.Entity.Expense;
import com.splitbill.splitbill.Entity.ExpenseShare;
import com.splitbill.splitbill.Entity.Participant;
import com.splitbill.splitbill.Repository.ExpenseRepository;
import com.splitbill.splitbill.Repository.ExpenseShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseShareRepository expenseShareRepository;

    @Autowired
    private BillService billService;

    @Autowired
    private ParticipantService participantService;

    public Expense addExpense(UUID billId, String description, BigDecimal amount,
                              UUID paidByParticipantId, List<UUID> sharedWithParticipantIds) {

        Bill bill = billService.getBillById(billId);
        Participant paidBy = participantService.getParticipantById(paidByParticipantId);

        Expense expense = new Expense();
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setBill(bill);
        expense.setPaidBy(paidBy);

        Expense savedExpense = expenseRepository.save(expense);

        for (UUID participantId : sharedWithParticipantIds) {
            Participant participant = participantService.getParticipantById(participantId);

            ExpenseShare share = new ExpenseShare();
            share.setExpense(savedExpense);
            share.setParticipant(participant);

            expenseShareRepository.save(share);
        }
        return savedExpense;
    }

    public List<Expense> getExpensesByBillId(UUID billId) {
        return expenseRepository.findByBillId(billId);
    }

}
