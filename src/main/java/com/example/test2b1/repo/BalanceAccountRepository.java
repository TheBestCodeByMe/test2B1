package com.example.test2b1.repo;

import com.example.test2b1.entity.BalanceAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceAccountRepository extends JpaRepository<BalanceAccount, Long> {
}
