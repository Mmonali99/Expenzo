package com.example.expensetrack.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monthly")
public class Monthly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long monthlyId;

    @Column(name = "monthly_percentage", nullable = false)
    private double monthlyPercentage;

    @Column(name = "monthly_amount", nullable = false)
    private double monthlyAmount;

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

    // Methods

    public int getDaysInTheMonth(int year, int month) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        return lastDayOfMonth.getDayOfMonth();
    }
}
