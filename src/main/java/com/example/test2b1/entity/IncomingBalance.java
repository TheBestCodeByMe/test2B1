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
@Table(name = "incoming_balance")
@ToString
public class IncomingBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "active_amount", nullable = false)
    private BigDecimal activeAmount;
    @Column(name = "passive_amount", nullable = false)
    private BigDecimal passiveAmount;
    @OneToOne (optional=false, mappedBy="incomingBalance")
    private Record record;

    public IncomingBalance() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomingBalance that = (IncomingBalance) o;
        return Objects.equals(id, that.id) && Objects.equals(activeAmount, that.activeAmount) && Objects.equals(passiveAmount, that.passiveAmount) && Objects.equals(record, that.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activeAmount, passiveAmount, record);
    }
}
