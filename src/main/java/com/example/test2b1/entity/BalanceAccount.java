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
@Table(name = "balance_account")
@ToString
public class BalanceAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "balance_acc_numb", nullable = false)
    private String balanceAccNumb;
    @OneToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="class_id", nullable = false)
    private Class classId;
    @OneToOne (optional=false, mappedBy="balanceAccount")
    private Record record;

    public BalanceAccount() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceAccount that = (BalanceAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(balanceAccNumb, that.balanceAccNumb) && Objects.equals(classId, that.classId) && Objects.equals(record, that.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balanceAccNumb, classId, record);
    }
}