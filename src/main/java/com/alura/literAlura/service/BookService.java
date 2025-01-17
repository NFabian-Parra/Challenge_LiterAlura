package com.alura.literAlura.service;

import com.alura.literAlura.dto.AuthorDTO;
import com.alura.literAlura.dto.BookDTO;
import java.util.List;

public interface BookService {
    BookDTO searchBookByTitle(String title);
    List<BookDTO> getAllBooks();
    List<AuthorDTO> getAllAuthors();
    List<AuthorDTO> getLivingAuthorsByYear(int year);
    List<BookDTO> getBooksByLanguage(String language);
    List<BookDTO> getTopDownloadedBooks();
}
