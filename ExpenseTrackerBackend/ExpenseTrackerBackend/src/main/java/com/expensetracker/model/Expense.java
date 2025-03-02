package com.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String purchaseLocation;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    public Expense(Double amount, String purchaseLocation, LocalDate date, LocalTime time, String description, List<Item> items, User user) {
        this.amount = amount;
        this.purchaseLocation = purchaseLocation;
        this.date = date;
        this.time = time;
        this.description = description;
        this.items = items;
        this.user = user;
    }
}