package com.example.test2b1.repo;

import com.example.test2b1.entity.Turnover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoverRepository extends JpaRepository<Turnover, Long> {
}
