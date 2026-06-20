package com.splitbill.splitbill.Repository;

import com.splitbill.splitbill.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {

    Optional<Bill> findByShareUrl(String shareUrl);
}
