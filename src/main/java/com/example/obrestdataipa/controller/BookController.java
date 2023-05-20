package com.example.obrestdataipa.controller;

import com.example.obrestdataipa.entities.Book;
import com.example.obrestdataipa.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/books")
    public List<Book> findAllBooks () {
        return (List<Book>) bookRepository.findAll();
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> findOneById (@PathVariable Long id) {
        Optional<Book> optBook = bookRepository.findById(id);

        //Opción 1
        return optBook.isPresent() ? ResponseEntity.ok(optBook.get()) : ResponseEntity.notFound().build();

        /**
         * Opción 2:
         if (optBook.isPresent()) {
              return optBook.get();
         } else {
              return null;
         }**/

    }

    @PostMapping("/api/create")
    public Book create (@RequestBody Book book) {
        return bookRepository.save(book);
    }
}
