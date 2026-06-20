package com.splitbill.splitbill.Service;


import com.splitbill.splitbill.Entity.Bill;
import com.splitbill.splitbill.Repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public Bill createBill(String name) {
        Bill bill = new Bill();
        bill.setName(name);
        bill.setShareUrl(UUID.randomUUID().toString());
        return billRepository.save(bill);
    }

    public Bill getBillByShareUrl(String shareUrl) {
        return billRepository.findByShareUrl(shareUrl)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    public Bill getBillById(UUID billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }
}
