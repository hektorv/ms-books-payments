package com.relatos.books.payments.model;

import lombok.Data;

@Data
public class PurchaseItem {
    private Long bookId;
    private int quantity;
}
