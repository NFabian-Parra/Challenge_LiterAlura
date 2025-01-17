package com.alura.literAlura.model;

import com.alura.literAlura.dto.GutendexBookDTO;

import java.util.List;

public record GutendexResponse(List<GutendexBookDTO> results, String next) {
}
