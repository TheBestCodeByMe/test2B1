package com.example.test2b1.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "turnovers")
@ToString
public class Turnover {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "debit_amount", nullable = false)
    private BigDecimal debitAmount;
    @Column(name = "credit_amount", nullable = false)
    private BigDecimal creditAmount;
    @OneToOne (optional=false, mappedBy="turnover")
    private Record record;

    public Turnover() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turnover turnover = (Turnover) o;
        return Objects.equals(id, turnover.id) && Objects.equals(debitAmount, turnover.debitAmount) && Objects.equals(creditAmount, turnover.creditAmount) && Objects.equals(record, turnover.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, debitAmount, creditAmount, record);
    }
}
