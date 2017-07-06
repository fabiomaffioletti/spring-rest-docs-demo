package com.documentation.demo.controller;

import com.documentation.demo.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fmaffioletti on 7/6/17.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return Book.builder().id(id).title("My test book").build();
    }

    @GetMapping
    public List<Book> findAll() {
        return Arrays.asList(Book.builder().id(100L).title("My test book").build(), Book.builder().id(101L).title("A beautiful book").build());
    }

    @PostMapping
    public Book save(@RequestBody Book book) {
        book.setId(102L);
        return book;
    }

}
