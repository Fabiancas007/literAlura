package com.alura.library.model;

import com.alura.library.dto.AuthorDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy = "authors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> books;

    // Constructor vacío
    public Author(){
    }

    public Author(List<AuthorDTO> author){
        if (author != null && !author.isEmpty()) {
            this.name = author.get(0).name();
            this.birthYear = author.get(0).dateOfBirth();
            this.deathYear = author.get(0).dateOfDead();
        } else {
            this.name = "Desconocido";
            this.birthYear = 0;
            this.deathYear = 0;
        }
    }

    @Override
    public String toString() {
        return  "Autor: " + name + "\n" +
                "Fecha de nacimiento: '" + birthYear + "\n" +
                "Decha de muerte: '" + deathYear + "\n" +
                "Libros: " + showBookTitles().get(0).toString();
    }

    private List<String> showBookTitles(){
        var title = books.stream().map(Book::getTitle).collect(Collectors.toList());
        return title;
    }

    // Setters y Getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
