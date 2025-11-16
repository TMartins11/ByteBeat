package com.mrtns.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @PostMapping("/books")
    public Book createBook(@RequestBody Book book){
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    @GetMapping("/books")
    public List<Book> retrieveAllBooks(){
        return bookRepository.findAll();
    }

    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book book){
        book.setId(id);
        return bookRepository.save(book);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id, @RequestBody Book book){
        book.setId(id);
        bookRepository.delete(book);
        return ResponseEntity.ok().build();
    }

}
