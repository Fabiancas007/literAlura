package com.alura.library.model;

import com.alura.library.dto.BookDTO;
import jakarta.persistence.*;

import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @ManyToOne
    @JoinColumn(name = "authors_id")
    private Author authors;
    @Enumerated(EnumType.STRING)
    private Language language;
    private Double numberOfDownload;

    // Constructor vacío
    public Book(){
    }

    public Book(BookDTO bookData){
        this.title = bookData.title();
        this.language = Language.fromString(bookData.laguages().stream()
                .limit(1).collect(Collectors.joining()));
        this.numberOfDownload = bookData.numberOfDownloads();
    }

    @Override
    public String toString() {
        return "----- LIBRO ----- \n" +
                "Título: '" + title + "\n" +
                ", Autor: '" + getAuthors() + "\n" +
                ", Idioma: " + language + "\n" +
                ", Número de descargas: " + numberOfDownload + "\n" +
                "-----------------";
    }

    // Setters y Getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        if(authors != null) {
            return authors.getName().split(",")[0].trim();
        }
        return "Desconocido";
    }

    public void setAuthors(Author authors) {
        this.authors = authors;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Double getNumberOfDownload() {
        return numberOfDownload;
    }

    public void setNumberOfDownload(Double numberOfDownload) {
        this.numberOfDownload = numberOfDownload;
    }
}
