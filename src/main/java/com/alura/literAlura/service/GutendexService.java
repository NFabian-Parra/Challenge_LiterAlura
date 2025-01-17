package com.alura.literAlura.service;

import com.alura.literAlura.dto.GutendexBookDTO;

import java.util.List;


public interface GutendexService {
    List<GutendexBookDTO> searchBooksByTitle(String title);
    List<GutendexBookDTO> getTopDownloadedBooks();
    List<GutendexBookDTO> searchAuthorsByYear(int year);
}