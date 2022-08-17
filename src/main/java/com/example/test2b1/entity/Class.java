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
@Table(name = "class")
@ToString
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "code", nullable = false)
    private int code;
    @Column(name = "description", nullable = false)
    private String description;
    @OneToOne (optional=false, mappedBy="classId")
    private BalanceAccount balanceAccount;

    public Class() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class aClass = (Class) o;
        return code == aClass.code && Objects.equals(id, aClass.id) && Objects.equals(description, aClass.description) && Objects.equals(balanceAccount, aClass.balanceAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, description, balanceAccount);
    }
}