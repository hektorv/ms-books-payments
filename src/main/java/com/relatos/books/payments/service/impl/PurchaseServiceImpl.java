package com.relatos.books.payments.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.relatos.books.payments.clients.CatalogueClient;
import com.relatos.books.payments.clients.CatalogueClientConfig;
import com.relatos.books.payments.controller.request.PurchaseRequest;
import com.relatos.books.payments.model.Book;
import com.relatos.books.payments.model.Purchase;
import com.relatos.books.payments.model.PurchaseItem;
import com.relatos.books.payments.model.StockUpdateRequest;
import com.relatos.books.payments.persistence.PurchaseEntity;
import com.relatos.books.payments.persistence.PurchaseItemEntity;
import com.relatos.books.payments.persistence.PurchaseRepository;
import com.relatos.books.payments.service.PurchaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{

    private final PurchaseRepository purchaseRepository;
    private final CatalogueClient catalogueClient;

@Override
public List<Purchase> getAllPurchases() {
    List<PurchaseEntity> entities = purchaseRepository.findAll();

    return entities.stream()
        .map(entity -> new Purchase(
            entity.getId(),
            entity.getBuyerEmail(),
            entity.getPurchaseDate(),
            entity.getItems().stream()
                .map(itemEntity -> new PurchaseItem(
                    itemEntity.getBookId(),
                    itemEntity.getQuantity()
                ))
                .collect(Collectors.toList())
        ))
        .collect(Collectors.toList());
}



    @Override
    @Transactional
    public void registerPurchase(PurchaseRequest request) {
        double total = 0.0;
        log.info("registerPurchase"+request.toString());
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Purchase must contain at least one item.");
        }
        for(var item:request.getItems()){
            Book book = catalogueClient.getBookById(item.getBookId());    
            total+= book.getPrice()*item.getQuantity();
        }
        
        List<StockUpdateRequest> updates = request.getItems().stream()
            .map(item -> new StockUpdateRequest(item.getBookId(), -item.getQuantity()))
            .collect(Collectors.toCollection(ArrayList::new));

        catalogueClient.registerPurchase(updates);

        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setBuyerEmail(request.getBuyerEmail());
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setTotal(total);

        List<PurchaseItemEntity> validatedItems = request.getItems().stream().map(item -> {
            PurchaseItemEntity newItem = new PurchaseItemEntity();
            newItem.setBookId(item.getBookId());
            newItem.setQuantity(item.getQuantity());
            newItem.setPurchase(purchase); 
            return newItem;
        }).toList();

        purchase.setItems(validatedItems);

        purchaseRepository.save(purchase);
    }
}
