package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    Page<Book> findByCategory(String category, Pageable pageable);

    Page<Book> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);
}
