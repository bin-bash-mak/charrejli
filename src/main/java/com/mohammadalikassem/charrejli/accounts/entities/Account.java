package com.mohammadalikassem.charrejli.accounts.entities;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

// Enum for Operator field


@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
public    enum Operator {
        ALFA,
        MTC
    }

    // Enum for Status field
    public enum Status {
        NEW,
        REST,
        PROCESSING,
        FAILED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private boolean active = false;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private double balance = 0;

    @Column
    private LocalDate validity;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

//    @Column(columnDefinition = "jsonb")
//    private JsonNode lastData;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Operator operator = Operator.MTC;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NEW;

}