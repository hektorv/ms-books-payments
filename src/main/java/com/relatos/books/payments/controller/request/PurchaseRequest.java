package com.relatos.books.payments.controller.request;

import java.util.List;
import lombok.Data;
import com.relatos.books.payments.model.PurchaseItem;

@Data
public class PurchaseRequest {
    private String buyerEmail;
    private List<PurchaseItem> items;
}
 