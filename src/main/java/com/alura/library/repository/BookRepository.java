package com.alura.library.repository;

import com.alura.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(concat('%', :title, '%'))")
    Optional<Book> checkExistenceOfTheBook(String title);

    @Query("SELECT b FROM Book b WHERE :languages = '' OR UPPER(b.language) = UPPER(:languages)")
    List<Book> showByLanguage(@Param("languages") String languages);
}
