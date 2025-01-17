package com.alura.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexBookDTO(
        String title,
        @JsonProperty("authors") List<GutendexAuthorDTO> authors,
        String language,
        @JsonProperty("download_count") int downloadCount
) {
}
