package com.alura.library.service;

import com.alura.library.model.Author;
import com.alura.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> showAuthors(){
        return  authorRepository.findAll();
    }

    public void saveAuthor(Author author){
        authorRepository.save(author);
    }

    public List<Author> searchAuthorsAliveInAGivenYear(Integer year) {
        return authorRepository.searchAuthorsAliveInAGivenYear(year);
    }

    public Optional<Author> checkExistenceOfTheAuthor(String name) {
        return authorRepository.checkExistenceOfTheAuthor(name);
    }
}
