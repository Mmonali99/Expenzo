package com.example.expensetrack.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "weekly")
public class Weekly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weeklyId;

    @Column(name = "week_number", nullable = false)
    private int weekNumber; // To identify the week in the month

    @Column(name = "weekly_percentage", nullable = false)
    private double weeklyPercentage;

    @Column(name = "weekly_amount", nullable = false)
    private double weeklyAmount;

    @Column(name = "spent_percentage", nullable = false)
    private double spentPercentage;

    @Column(name = "spent_amount", nullable = false)
    private double spentAmount;

    @Column(name = "remaining_percentage", nullable = false)
    private double remainingPercentage;

    @Column(name = "remaining_amount", nullable = false)
    private double remainingAmount;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
