package com.alura.literAlura.controller;

import com.alura.literAlura.dto.AuthorDTO;
import com.alura.literAlura.dto.BookDTO;
import com.alura.literAlura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public BookDTO searchBookByTitle(String title) {
        return bookService.searchBookByTitle(title);
    }

    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    public List<AuthorDTO> getAllAuthors() {
        return bookService.getAllAuthors();
    }

    public List<AuthorDTO> getLivingAuthorsByYear(int year) {
        return bookService.getLivingAuthorsByYear(year);
    }

    public List<BookDTO> getBooksByLanguage(String language) {
        return bookService.getBooksByLanguage(language);
    }

    public List<BookDTO> getTopDownloadedBooks() {
        return bookService.getTopDownloadedBooks();
    }
}
