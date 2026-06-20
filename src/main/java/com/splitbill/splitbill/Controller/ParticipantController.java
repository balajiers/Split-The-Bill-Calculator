package com.splitbill.splitbill.Controller;


import com.splitbill.splitbill.Entity.Participant;
import com.splitbill.splitbill.Service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bills/{billId}/participants")
@CrossOrigin(origins = "*")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @PostMapping
    public Participant addParticipant(@PathVariable UUID billId, @RequestBody AddParticipantRequest request) {
        return participantService.addParticipant(billId, request.getName(), request.getUpiId());
    }

    @GetMapping
    public List<Participant> getParticipants(@PathVariable UUID billId) {
        return participantService.getParticipantsByBillId(billId);
    }

    public static class AddParticipantRequest {
        private String name;
        private String upiId;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUpiId() { return upiId; }
        public void setUpiId(String upiId) { this.upiId = upiId; }
    }

}
