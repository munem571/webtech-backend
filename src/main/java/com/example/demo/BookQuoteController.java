package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BookQuoteController {

    private final BookQuoteRepository repository;

    public BookQuoteController(BookQuoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/v1/quotes")
    public List<BookQuote> getAllQuotes() {
        return repository.findAll();
    }

    @PostMapping("/api/v1/quotes")
    @ResponseStatus(HttpStatus.CREATED)
    public BookQuote createQuote(@RequestBody BookQuote quote) {
        quote.setId(null);
        return repository.save(quote);
    }
}