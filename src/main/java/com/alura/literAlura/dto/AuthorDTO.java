package com.alura.literAlura.dto;

import java.time.LocalDate;
import java.util.List;

public record AuthorDTO(
        Long id,
        String name,
        LocalDate birthDate,
        LocalDate deathDate,
        List<BookDTO> books
) {}
