package com.alura.library.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<AuthorDTO> authors,
        @JsonAlias("languages") List<String> laguages,
        @JsonAlias("download_count") Double numberOfDownloads
    ) {
}
