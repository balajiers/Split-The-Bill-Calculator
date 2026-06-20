package com.splitbill.splitbill.Controller;


import com.splitbill.splitbill.Entity.Bill;
import com.splitbill.splitbill.Service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "*")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public Bill createBill(@RequestBody CreateBillRequest request) {
        return billService.createBill(request.getName());
    }

    @GetMapping("/{shareUrl}")
    public Bill getBill(@PathVariable String shareUrl) {
        return billService.getBillByShareUrl(shareUrl);
    }

    public static class CreateBillRequest {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
