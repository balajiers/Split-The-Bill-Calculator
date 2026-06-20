package com.splitbill.splitbill.Service;

import com.splitbill.splitbill.Entity.Bill;
import com.splitbill.splitbill.Entity.Participant;
import com.splitbill.splitbill.Repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private BillService billService;

    @Autowired
    private ParticipantRepository participantRepository;

    public Participant addParticipant(UUID billId, String name, String upiId) {
        Bill bill = billService.getBillById(billId);

        Participant participant = new Participant();
        participant.setName(name);
        participant.setUpiId(upiId);
        participant.setBill(bill);

        return participantRepository.save(participant);
    }

    public List<Participant> getParticipantsByBillId(UUID billId) {
        return participantRepository.findByBillId(billId);
    }

    public Participant getParticipantById(UUID participantId) {
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant not found"));
    }
}
