package com.example.test2b1.repo;

import com.example.test2b1.entity.OutgoingBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutgoingBalanceRepository extends JpaRepository<OutgoingBalance, Long> {
}
