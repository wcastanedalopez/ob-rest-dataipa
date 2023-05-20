package com.example.obrestdataipa.controller;

import com.example.obrestdataipa.entities.Book;
import com.example.obrestdataipa.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final Logger log = LoggerFactory.getLogger(BookController.class);

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

    @PostMapping("/api/books")
    public ResponseEntity<Book> create (@RequestBody Book book) {

        if (book.getId() != null) {
            log.warn("Traying to create book with id");
            return ResponseEntity.badRequest().build();
        }
        Book aux = bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/api/books")
    public ResponseEntity<Book> update (@RequestBody Book book){

        if (book.getId() == null) {
            log.warn("Traying to update a non existing book");
            return ResponseEntity.badRequest().build();
        }
        if (!bookRepository.existsById(book.getId()) ) {
            log.warn("Traying to update a non existing book");
            return ResponseEntity.notFound().build();
        }
        Book aux = bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> deleteBook (@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            log.warn("Traying to delete a non existing book");
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAllBook () {
        if (bookRepository.count() != 0) {
            log.warn("Traying to delete a non existing book");
            return ResponseEntity.notFound().build();

        }
        log.warn("Deleting all books");
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
