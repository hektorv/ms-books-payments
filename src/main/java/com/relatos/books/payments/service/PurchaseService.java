package com.relatos.books.payments.service;

import java.util.List;
import com.relatos.books.payments.controller.request.PurchaseRequest;
import com.relatos.books.payments.model.Purchase;

public interface PurchaseService {

    public List<Purchase> getAllPurchases();
    
    public void registerPurchase(PurchaseRequest request);
}
