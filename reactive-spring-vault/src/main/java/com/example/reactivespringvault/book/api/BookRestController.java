package com.example.reactivespringvault.book.api;

import com.example.reactivespringvault.book.dataaccess.Book;
import com.example.reactivespringvault.book.service.BookService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Flux<Book> books() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Book> bookById(@PathVariable("id") String id) {
        return bookService.findById(id);
    }

    @PostMapping("/{id}")
    public Mono<Book> createBook(@PathVariable("id") String id, @RequestBody CreateBook createBook) {
        Book book = new Book(id, createBook.getTitle(), createBook.getDescription());
        return bookService.save(book);
    }

}
