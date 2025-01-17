package com.alura.literAlura.service.impl;

import com.alura.literAlura.dto.AuthorDTO;
import com.alura.literAlura.dto.BookDTO;
import com.alura.literAlura.dto.GutendexBookDTO;
import com.alura.literAlura.entity.Author;
import com.alura.literAlura.entity.Book;
import com.alura.literAlura.exception.GutendexException;
import com.alura.literAlura.repository.AuthorRepository;
import com.alura.literAlura.repository.BookRepository;
import com.alura.literAlura.service.BookService;
import com.alura.literAlura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GutendexService gutendexService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GutendexService gutendexService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.gutendexService = gutendexService;
    }

    @Override
    @Transactional
    public BookDTO searchBookByTitle(String title) {
        List<GutendexBookDTO> gutendexBooks = gutendexService.searchBooksByTitle(title);
        if (gutendexBooks.isEmpty()) {
            throw new GutendexException("No se han encontrado libros con ese nombre");
        }
        GutendexBookDTO gutendexBook = gutendexBooks.get(0);
        Optional<Book> existingBook = bookRepository.findAll().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(gutendexBook.title()))
                .findFirst();
        if (existingBook.isPresent()) {
            throw new GutendexException("El libro ya existe en la base de datos");
        }
        Book book = createBookFromGutendex(gutendexBook);
        bookRepository.save(book);
        return mapBookToDTO(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(this::mapBookToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(this::mapAuthorToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AuthorDTO> getLivingAuthorsByYear(int year) {
        List<GutendexBookDTO> gutendexAuthors = gutendexService.searchAuthorsByYear(year);
        return gutendexAuthors.stream()
                .map(gutendexBookDTO -> gutendexBookDTO.authors().get(0))
                .map(gutendexAuthorDTO -> new AuthorDTO(
                        null,
                        gutendexAuthorDTO.name(),
                        parseDate(gutendexAuthorDTO.birth_year()),
                        parseDate(gutendexAuthorDTO.death_year()),
                        new ArrayList<>()
                )).collect(Collectors.toList());
    }

    private LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.equals("")) {
            return null;
        }
        try {
            return LocalDate.parse(dateString + "-01-01");
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public List<BookDTO> getBooksByLanguage(String language) {
        return bookRepository.findByLanguageIgnoreCase(language).stream().map(this::mapBookToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<BookDTO> getTopDownloadedBooks() {
        List<GutendexBookDTO> gutendexBooks = gutendexService.getTopDownloadedBooks();
        return gutendexBooks.stream()
                .map(this::createBookFromGutendex)
                .map(book -> bookRepository.save(book))
                .map(this::mapBookToDTO)
                .collect(Collectors.toList());
    }

    private Book createBookFromGutendex(GutendexBookDTO gutendexBook) {
        Author author = createAuthorFromGutendex(gutendexBook);
        author = authorRepository.save(author);
        Book book = new Book();
        book.setTitle(gutendexBook.title());
        book.setLanguage(gutendexBook.language());
        book.setDownloads(gutendexBook.downloadCount());
        book.setAuthor(author);
        return book;
    }

    private Author createAuthorFromGutendex(GutendexBookDTO gutendexBook) {
        var gutendexAuthor = gutendexBook.authors().get(0);
        Author author = new Author();
        author.setName(gutendexAuthor.name());
        author.setBirthDate(parseDate(gutendexAuthor.birth_year()));
        author.setDeathDate(parseDate(gutendexAuthor.death_year()));
        return author;
    }

    private BookDTO mapBookToDTO(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getLanguage(), book.getDownloads(), mapAuthorToDTO(book.getAuthor()));
    }

    private AuthorDTO mapAuthorToDTO(Author author) {
        return new AuthorDTO(author.getId(), author.getName(), author.getBirthDate(), author.getDeathDate(),
                author.getBooks() == null ? new ArrayList<>(): author.getBooks().stream().map(this::mapBookToDTO).collect(Collectors.toList()));
    }
}
