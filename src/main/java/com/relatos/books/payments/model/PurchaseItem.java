package com.relatos.books.payments.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseItem {
    private Long bookId;
    private int quantity;
}
