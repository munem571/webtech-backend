package com.example.demo;

public class BookQuote {
    private Long id;
    private String bookTitle;
    private String passageText;
    private int pageNumber;
    private String category;

    public BookQuote() {}

    public BookQuote(Long id, String bookTitle, String passageText, int pageNumber, String category) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.passageText = passageText;
        this.pageNumber = pageNumber;
        this.category = category;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getPassageText() { return passageText; }
    public void setPassageText(String passageText) { this.passageText = passageText; }

    public int getPageNumber() { return pageNumber; }
    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}