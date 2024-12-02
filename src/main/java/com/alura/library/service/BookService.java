package com.alura.library.service;

import com.alura.library.model.Book;
import com.alura.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> showBooks(){
        return bookRepository.findAll();
    }

    public void saveBook(Book book){
        bookRepository.save(book);
    }

    public List<Book> showByLanguage(String lenguage){
        return bookRepository.showByLanguage(lenguage);
    }

    public Optional<Book> checkExistenceOfTheBook(String title){
        return bookRepository.checkExistenceOfTheBook(title);
    }

}
