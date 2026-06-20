package com.splitbill.splitbill.Repository;

import com.splitbill.splitbill.Entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    List<Participant> findByBillId(UUID billId);
}
