package com.example.test2b1.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "file")
@ToString
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "date_since", nullable = false)
    private Date dateSince;
    @Column(name = "date_to", nullable = false)
    private Date dateTo;
    @Column(name = "filling_time", nullable = false)
    private LocalDate fillingTime;
    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="bank_id", nullable = false)
    private Bank bankId;
    @OneToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="currency_id", nullable = false)
    private Currency currencyId;
    @OneToMany (mappedBy="fileId", fetch=FetchType.EAGER)
    private List<Record> records;

    public File() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(id, file.id) && Objects.equals(name, file.name) && Objects.equals(dateSince, file.dateSince) && Objects.equals(dateTo, file.dateTo) && Objects.equals(fillingTime, file.fillingTime) && Objects.equals(bankId, file.bankId) && Objects.equals(currencyId, file.currencyId) && Objects.equals(records, file.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateSince, dateTo, fillingTime, bankId, currencyId, records);
    }
}