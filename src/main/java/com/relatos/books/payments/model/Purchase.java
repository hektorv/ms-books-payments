package com.relatos.books.payments.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Purchase {
    private String id;
    private String buyerEmail;
    private LocalDateTime purchaseDate;
    private List<PurchaseItem> items;
}