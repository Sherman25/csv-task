package com.example.csvtask.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Entity representing a bank Card.
 *
 * @author Otabek Sherman
 * @since 2022/09/20
 */
@Entity
@Table(name = "cards")
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(
            name = "card_number",
            nullable = false,
            unique = true,
            columnDefinition = "numeric(16, 0)"
    )
    @CsvBindByName(column = "card_number")
    private BigInteger cardNumber;

    @Column(
            name = "card_holder",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @CsvBindByName(column = "card_holder")
    private String holderName;
}
