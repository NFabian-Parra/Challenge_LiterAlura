package com.alura.literAlura.service.impl;

import com.alura.literAlura.controller.BookController;
import com.alura.literAlura.dto.AuthorDTO;
import com.alura.literAlura.dto.BookDTO;
import com.alura.literAlura.exception.GutendexException;
import com.alura.literAlura.service.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final BookController bookController;
    private final Scanner scanner;

    @Autowired
    public ConsoleServiceImpl(BookController bookController) {
        this.bookController = bookController;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            mostrarMenu();
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        listarLibrosAlmacenados();
                        break;
                    case 3:
                        listarAutoresAlmacenados();
                        break;
                    case 4:
                        listarAutoresVivosEnAnio();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 6:
                        listarTop5Descargados();
                        break;
                    case 7:
                        System.out.println("Saliendo de la aplicación.");
                        return;
                    default:
                        System.out.println("Opción inválida, por favor ingrese el número de la opción.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Caracter inválido, por favor ingrese el número de la opción.");
            }
            System.out.println();
        }
    }

    private void mostrarMenu() {
        System.out.println("--- Menú ---");
        System.out.println("1. Buscar libros por título.");
        System.out.println("2. Listado de libros almacenados.");
        System.out.println("3. Listado de autores almacenados.");
        System.out.println("4. Listado de autores vivos en determinado año.");
        System.out.println("5. Listado de libros por idioma.");
        System.out.println("6. Top 5 de los más descargados.");
        System.out.println("7. Salir.");
        System.out.print("Ingrese una opción: ");
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Ingrese el título del libro a buscar: ");
        String titulo = scanner.nextLine();
        try {
            BookDTO book = bookController.searchBookByTitle(titulo);
            System.out.println("Información del libro encontrado:");
            System.out.println("  Título: " + book.title());
            System.out.println("  Autor: " + book.author().name());
            System.out.println("  Idioma: " + book.language());
            System.out.println("  Número de descargas: " + book.downloads());
            System.out.println("Libro almacenado en tu base de datos");
        } catch (GutendexException e) {
            System.out.println(e.getMessage());
        }
    }
    private void listarLibrosAlmacenados() {
        List<BookDTO> books = bookController.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No hay libros almacenados.");
            return;
        }
        System.out.println("--- Libros Almacenados ---");
        books.forEach(book -> {
            System.out.println("  Título: " + book.title());
            System.out.println("  Autor: " + book.author().name());
            System.out.println("  Idioma: " + book.language());
            System.out.println("  Descargas: " + book.downloads());
            System.out.println("---------------------");
        });
    }

    private void listarAutoresAlmacenados() {
        List<AuthorDTO> authors = bookController.getAllAuthors();
        if(authors.isEmpty()){
            System.out.println("No hay autores almacenados.");
            return;
        }
        System.out.println("--- Autores Almacenados ---");
        authors.forEach(author -> {
            System.out.println("  Nombre: " + author.name());
            System.out.println("  Fecha de Nacimiento: " + author.birthDate());
            System.out.println("  Fecha de Fallecimiento: " + author.deathDate());
            if(!author.books().isEmpty()){
                System.out.println("  Libros: ");
                author.books().forEach(book -> System.out.println("  - " + book.title()));
            }
            System.out.println("---------------------");
        });
    }

    private void listarAutoresVivosEnAnio() {
        System.out.print("Ingrese el año para buscar autores vivos: ");
        try {
            int year = Integer.parseInt(scanner.nextLine());
            List<AuthorDTO> authors = bookController.getLivingAuthorsByYear(year);
            if(authors.isEmpty()){
                System.out.println("No se encontraron autores vivos en ese año.");
                return;
            }
            System.out.println("--- Autores Vivos en " + year + " ---");
            authors.forEach(author -> {
                System.out.println("  Nombre: " + author.name());
                System.out.println("  Fecha de Nacimiento: " + author.birthDate());
                System.out.println("  Fecha de Fallecimiento: " + author.deathDate());
                System.out.println("---------------------");
            });
        }catch (NumberFormatException e){
            System.out.println("Por favor ingrese un año válido");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Elija un idioma: ");
        System.out.println("1. Español");
        System.out.println("2. Inglés");
        System.out.println("3. Francés");
        System.out.print("Ingrese el número del idioma: ");

        String language;
        try {
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    language = "Spanish";
                    break;
                case 2:
                    language = "English";
                    break;
                case 3:
                    language = "French";
                    break;
                default:
                    System.out.println("Opción de idioma inválida.");
                    return;
            }
            List<BookDTO> books = bookController.getBooksByLanguage(language);
            if (books.isEmpty()) {
                System.out.println("No hay libros almacenados en este idioma.");
                return;
            }
            System.out.println("--- Libros en " + language + " ---");
            books.forEach(book -> {
                System.out.println("  Título: " + book.title());
                System.out.println("  Autor: " + book.author().name());
                System.out.println("  Idioma: " + book.language());
                System.out.println("  Descargas: " + book.downloads());
                System.out.println("---------------------");
            });
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese una opción válida");
        }
    }

    private void listarTop5Descargados() {
        List<BookDTO> books = bookController.getTopDownloadedBooks();
        if (books.isEmpty()) {
            System.out.println("No se encontraron libros para mostrar.");
            return;
        }
        System.out.println("--- Top 5 Libros Más Descargados ---");
        books.forEach(book -> {
            System.out.println("  Título: " + book.title());
            System.out.println("  Autor: " + book.author().name());
            System.out.println("  Idioma: " + book.language());
            System.out.println("  Descargas: " + book.downloads());
            System.out.println("---------------------");
        });
    }
}
