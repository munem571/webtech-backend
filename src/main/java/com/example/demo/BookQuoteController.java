package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookQuoteController {

    @GetMapping("/api/v1/quotes")
    public List<BookQuote> getAllQuotes() {
        return List.of(
                new BookQuote(1L, "Clean Code", "Even bad code can function. But if code isn't clean, it can bring a development organization to its knees.", 14, "INSPIRATION"),
                new BookQuote(2L, "Der Erlkönig", "Wer reitet so spät durch Nacht und Wind? Es ist der Vater mit seinem Kind.", 1, "KLASSIK"),
                new BookQuote(3L, "1984", "Who controls the past controls the future. Who controls the present controls the past.", 35, "ROMAN")
        );
    }
}