package com.example.expensetrack.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Double itemAmount;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Add this constructor
    public Item(Long itemId, String itemName, Double itemAmount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemAmount = itemAmount;
    }

}
