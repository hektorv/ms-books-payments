package com.relatos.books.payments.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.relatos.books.payments.controller.request.PurchaseRequest;
import com.relatos.books.payments.model.Purchase;

import java.util.List;

@Tag(name = "Book Purchases API", description = "REST API for managing and registering book purchases.")
@RequestMapping("/api/purchases")
public interface PurchasesApi {

    @Operation(
        summary = "List all registered purchases",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of purchases",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Purchase.class)))
            )
        }
    )
    @GetMapping
    ResponseEntity<List<Purchase>> listAllPurchases();

    @Operation(
        summary = "Register a new purchase",
        description = "Registers a new book purchase with multiple items.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PurchaseRequest.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid"),
            @ApiResponse(responseCode = "404", description = "Not found")
        }
    )
    @PostMapping
    ResponseEntity<Void> registerPurchase(PurchaseRequest request);
}