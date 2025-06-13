package com.relatos.books.payments.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Purchase {
    public Purchase(Long id, String buyerEmail, LocalDateTime purchaseDate,List<PurchaseItem> items) {
        this.id = id;
        this.buyerEmail = buyerEmail;
        this.purchaseDate = purchaseDate;
        this.items = items;
    }
    private Long id;
    private String buyerEmail;
    private LocalDateTime purchaseDate;
    private List<PurchaseItem> items;
}