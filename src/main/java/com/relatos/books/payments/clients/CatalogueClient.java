package com.relatos.books.payments.clients;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.relatos.books.payments.model.Book;
import com.relatos.books.payments.model.StockUpdateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogueClient {
    
    private final RestClient client;
 
    public Book getBookById(Long bookId) {
        try {
            return client
                    .get()
                    .uri("/{id}", bookId)
                    .retrieve()
                    .body(Book.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Book not found: " + bookId);
        }
    }

    public void registerPurchase(List<StockUpdateRequest> updates) {
        log.info("Sending stock update request: {}", updates);

        try {
            ResponseEntity<Void> response = client
                .post()
                .uri("/stock")
                .body(updates)
                .retrieve()
                .toBodilessEntity();

            log.info("Stock updated successfully. Status: {}", response.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            log.error("Book not found when updating stock: {}", e.getMessage());
            throw new IllegalArgumentException("Book not found", e);
        } catch (HttpClientErrorException.BadRequest e) {
            log.error("Invalid stock update request: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid stock update request", e);
        } catch (HttpClientErrorException e) {
            log.error("Client error from catalogue: {}", e.getMessage());
            throw new IllegalStateException("Unexpected error from catalogue", e);
        } catch (Exception e) {
            log.error("Unexpected error calling catalogue service: {}", e.getMessage());
            throw new IllegalStateException("Catalogue service failed", e);
        }
    }
}
