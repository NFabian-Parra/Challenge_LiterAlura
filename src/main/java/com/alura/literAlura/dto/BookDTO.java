package com.alura.literAlura.dto;

public record BookDTO(
        Long id,
        String title,
        String language,
        Integer downloads,
        AuthorDTO author
) {}
