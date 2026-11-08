package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookQuoteRepository extends JpaRepository<BookQuote, Long> {
}