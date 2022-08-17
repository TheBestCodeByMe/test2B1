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
@Table(name = "outgoing_balance")
@ToString
public class OutgoingBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "active_amount", nullable = false)
    private BigDecimal activeAmount;
    @Column(name = "passive_amount", nullable = false)
    private BigDecimal passiveAmount;
    @OneToOne (optional=false, mappedBy="outgoingBalance")
    private Record record;

    public OutgoingBalance() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutgoingBalance that = (OutgoingBalance) o;
        return Objects.equals(id, that.id) && Objects.equals(activeAmount, that.activeAmount) && Objects.equals(passiveAmount, that.passiveAmount) && Objects.equals(record, that.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activeAmount, passiveAmount, record);
    }
}
