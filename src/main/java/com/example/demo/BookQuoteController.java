package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @PutMapping("/api/v1/quotes/{id}")
    public BookQuote updateQuote(
            @PathVariable("id") Long id,
            @RequestBody BookQuote updatedQuote) {

        BookQuote quote = findQuote(id);

        quote.setBookTitle(updatedQuote.getBookTitle());
        quote.setPassageText(updatedQuote.getPassageText());
        quote.setPageNumber(updatedQuote.getPageNumber());
        quote.setCategory(updatedQuote.getCategory());

        return repository.save(quote);
    }

    @DeleteMapping("/api/v1/quotes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuote(@PathVariable("id") Long id) {
        BookQuote quote = findQuote(id);
        repository.delete(quote);
    }

    private BookQuote findQuote(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Zitat nicht gefunden"
                ));
    }
}