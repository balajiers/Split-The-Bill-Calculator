package com.splitbill.splitbill.Service;


import com.splitbill.splitbill.Entity.Expense;
import com.splitbill.splitbill.Entity.ExpenseShare;
import com.splitbill.splitbill.Entity.Participant;
import com.splitbill.splitbill.Repository.ExpenseShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class SettlementService {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseShareRepository expenseShareRepository;

    public static class Settlement {
        public String fromName;
        public String toName;
        public BigDecimal amount;
        public String upiLink;

        public Settlement(String fromName, String toName, BigDecimal amount, String upiLink) {
            this.fromName = fromName;
            this.toName = toName;
            this.amount = amount;
            this.upiLink=upiLink;
        }
    }

    public List<Settlement> calculateSettlement(UUID billId) {
        List<Participant> participants = participantService.getParticipantsByBillId(billId);
        List<Expense> expenses = expenseService.getExpensesByBillId(billId);

        Map<UUID, BigDecimal> balances = new HashMap<>();
        for (Participant p : participants) {
            balances.put(p.getId(), BigDecimal.ZERO);
        }
        for (Expense expense : expenses) {
            UUID payerId = expense.getPaidBy().getId();
            balances.put(payerId, balances.get(payerId).add(expense.getAmount()));

            List<ExpenseShare> shares = expenseShareRepository.findByExpenseId(expense.getId());
            BigDecimal shareAmount = expense.getAmount()
                    .divide(BigDecimal.valueOf(shares.size()), 2, BigDecimal.ROUND_HALF_UP);

            for (ExpenseShare share : shares) {
                UUID owerId = share.getParticipant().getId();
                balances.put(owerId, balances.get(owerId).subtract(shareAmount));
            }
        }
        return greedySettle(participants, balances);
    }
    private List<Settlement> greedySettle(List<Participant> participants, Map<UUID, BigDecimal> balances) {
        List<Settlement> result = new ArrayList<>();

        Map<UUID, String> names = new HashMap<>();
        Map<UUID, String> upiIds = new HashMap<>();
        for (Participant p : participants) {
            names.put(p.getId(), p.getName());
            upiIds.put(p.getId(), p.getUpiId());
        }

        List<UUID> ids = new ArrayList<>(balances.keySet());

        while (true) {
            UUID maxCreditor = null, maxDebtor = null;
            for (UUID id : ids) {
                BigDecimal val = balances.get(id);
                if (maxCreditor == null || val.compareTo(balances.get(maxCreditor)) > 0) maxCreditor = id;
                if (maxDebtor == null || val.compareTo(balances.get(maxDebtor)) < 0) maxDebtor = id;
            }

            BigDecimal creditorVal = balances.get(maxCreditor);
            BigDecimal debtorVal = balances.get(maxDebtor);

            if (creditorVal.compareTo(BigDecimal.valueOf(0.5)) <= 0
                    || debtorVal.compareTo(BigDecimal.valueOf(-0.5)) >= 0) {
                break;
            }

            BigDecimal amount = creditorVal.min(debtorVal.abs());

            String upiLink = buildUpiLink(upiIds.get(maxCreditor), names.get(maxCreditor), amount);
            result.add(new Settlement(names.get(maxDebtor), names.get(maxCreditor), amount,upiLink));

            balances.put(maxCreditor, creditorVal.subtract(amount));
            balances.put(maxDebtor, debtorVal.add(amount));
        }

        return result;
    }
    private String buildUpiLink(String upiId, String payeeName, BigDecimal amount) {
        if (upiId == null || upiId.isBlank()) {
            return null;
        }
        String encodedName = payeeName.replace(" ", "%20");
        return "upi://pay?pa=" + upiId + "&pn=" + encodedName + "&am=" + amount + "&cu=INR";
    }

}
