package com.alura.library.repository;

import com.alura.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT DISTINCT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear >= :year)")
    List<Author> searchAuthorsAliveInAGivenYear(Integer year);

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(concat('%', :name, '%'))")
    Optional<Author> checkExistenceOfTheAuthor(@Param("name") String name);

}
