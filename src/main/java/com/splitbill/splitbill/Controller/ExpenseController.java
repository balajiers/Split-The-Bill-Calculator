package com.splitbill.splitbill.Controller;


import com.splitbill.splitbill.Entity.Expense;
import com.splitbill.splitbill.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bills/{billId}/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Expense addExpense(@PathVariable UUID billId, @RequestBody AddExpenseRequest request) {
        return expenseService.addExpense(
                billId,
                request.getDescription(),
                request.getAmount(),
                request.getPaidByParticipantId(),
                request.getSharedWithParticipantIds()
        );
    }
    @GetMapping
    public List<Expense> getExpenses(@PathVariable UUID billId) {
        return expenseService.getExpensesByBillId(billId);
    }

    public static class AddExpenseRequest {
        private String description;
        private BigDecimal amount;
        private UUID paidByParticipantId;
        private List<UUID> sharedWithParticipantIds;

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        public UUID getPaidByParticipantId() { return paidByParticipantId; }
        public void setPaidByParticipantId(UUID paidByParticipantId) { this.paidByParticipantId = paidByParticipantId; }
        public List<UUID> getSharedWithParticipantIds() { return sharedWithParticipantIds; }
        public void setSharedWithParticipantIds(List<UUID> sharedWithParticipantIds) { this.sharedWithParticipantIds = sharedWithParticipantIds; }
    }


}
