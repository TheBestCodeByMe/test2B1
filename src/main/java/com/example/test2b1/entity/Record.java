package com.example.test2b1.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "record")
@ToString
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="file_id", nullable = false)
    private File fileId;
    @OneToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="incoming_balance_id", nullable = false)
    private IncomingBalance incomingBalance;
    @OneToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="outgoing_balance_id", nullable = false)
    private OutgoingBalance outgoingBalance;
    @OneToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="turnovers_id", nullable = false)
    private Turnover turnover;
    @OneToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="balance_acc_id", nullable = false)
    private BalanceAccount balanceAccount;

    public Record() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(id, record.id) && Objects.equals(fileId, record.fileId) && Objects.equals(incomingBalance, record.incomingBalance) && Objects.equals(outgoingBalance, record.outgoingBalance) && Objects.equals(turnover, record.turnover) && Objects.equals(balanceAccount, record.balanceAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileId, incomingBalance, outgoingBalance, turnover, balanceAccount);
    }
}
