package com.alura.library.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDTO(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer dateOfBirth,
        @JsonAlias("death_year") Integer dateOfDead
    ) {
}
