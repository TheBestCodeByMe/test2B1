package com.example.test2b1.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "bank")
@ToString
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany (mappedBy="bankId", fetch=FetchType.EAGER)
    private List<File> files;

    public Bank() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(name, bank.name) && Objects.equals(files, bank.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, files);
    }
}
