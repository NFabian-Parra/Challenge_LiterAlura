package com.alura.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexAuthorDTO(String name,
                                String birth_year,
                                String death_year) {
}
