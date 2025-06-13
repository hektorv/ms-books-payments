package com.relatos.books.payments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private String description;
    private String coverImage;
    private String category;
    private Integer publishYear;
    private String genre;
    private Integer pages;
    private String isbn;
    private Integer rating;
    private Boolean visible;
    private Integer stock;
}
