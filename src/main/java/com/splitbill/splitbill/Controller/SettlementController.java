package com.splitbill.splitbill.Controller;


import com.splitbill.splitbill.Service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bills/{billId}/settlement")
@CrossOrigin(origins = "*")
public class SettlementController {

    @Autowired
    private SettlementService settlementService;

    @GetMapping
    public List<SettlementService.Settlement> getSettlement(@PathVariable UUID billId) {
        return settlementService.calculateSettlement(billId);
    }

}
