package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemoApplicationTests {

	@Mock
	private BookQuoteRepository repository;

	private BookQuoteController controller;

	@BeforeEach
	void setUp() {
		controller = new BookQuoteController(repository);
	}

	@Test
	void getAllQuotesReturnsStoredQuotes() {
		BookQuote quote = new BookQuote(
				1L, "Testbuch", "Testzitat", 10, "TEST"
		);

		when(repository.findAll()).thenReturn(List.of(quote));

		List<BookQuote> result = controller.getAllQuotes();

		assertEquals(1, result.size());
		assertEquals("Testbuch", result.getFirst().getBookTitle());
		verify(repository).findAll();
	}

	@Test
	void createQuoteIgnoresSubmittedIdAndReturnsSavedQuote() {
		BookQuote submitted = new BookQuote(
				99L, "Neues Buch", "Neues Zitat", 20, "ROMAN"
		);

		when(repository.save(any(BookQuote.class))).thenAnswer(invocation -> {
			BookQuote quote = invocation.getArgument(0);

			// Vor dem Speichern darf keine fremde ID übernommen werden.
			assertNull(quote.getId());
			quote.setId(1L);
			return quote;
		});

		BookQuote result = controller.createQuote(submitted);

		assertEquals(Long.valueOf(1), result.getId());
		assertEquals("Neues Buch", result.getBookTitle());
		verify(repository).save(submitted);
	}

	@Test
	void updateQuoteChangesFieldsAndKeepsExistingId() {
		BookQuote existing = new BookQuote(
				1L, "Alter Titel", "Alter Text", 10, "ALT"
		);
		BookQuote changes = new BookQuote(
				99L, "Neuer Titel", "Neuer Text", 30, "NEU"
		);

		when(repository.findById(1L)).thenReturn(Optional.of(existing));
		when(repository.save(existing)).thenReturn(existing);

		BookQuote result = controller.updateQuote(1L, changes);

		assertEquals(Long.valueOf(1), result.getId());
		assertEquals("Neuer Titel", result.getBookTitle());
		assertEquals("Neuer Text", result.getPassageText());
		assertEquals(30, result.getPageNumber());
		assertEquals("NEU", result.getCategory());
		verify(repository).save(existing);
	}

	@Test
	void deleteQuoteDeletesExistingQuote() {
		BookQuote existing = new BookQuote(
				1L, "Testbuch", "Testzitat", 10, "TEST"
		);

		when(repository.findById(1L)).thenReturn(Optional.of(existing));

		controller.deleteQuote(1L);

		verify(repository).delete(existing);
	}

	@Test
	void updateMissingQuoteReturns404AndDoesNotSave() {
		when(repository.findById(42L)).thenReturn(Optional.empty());

		ResponseStatusException error = assertThrows(
				ResponseStatusException.class,
				() -> controller.updateQuote(42L, new BookQuote())
		);

		assertEquals(404, error.getStatusCode().value());
		verify(repository, never()).save(any(BookQuote.class));
	}

	@Test
	void deleteMissingQuoteReturns404AndDoesNotDelete() {
		when(repository.findById(42L)).thenReturn(Optional.empty());

		ResponseStatusException error = assertThrows(
				ResponseStatusException.class,
				() -> controller.deleteQuote(42L)
		);

		assertEquals(404, error.getStatusCode().value());
		verify(repository, never()).delete(any(BookQuote.class));
	}
}