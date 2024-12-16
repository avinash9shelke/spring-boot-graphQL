package com.example.graphql.resolver;

/**
 * @author avinash
 */

import com.example.graphql.entity.Book;
import com.example.graphql.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BookResolver {

    private final BookRepository bookRepository;

    public BookResolver(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Query: Get all books
    @QueryMapping
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    // Query: Get a book by ID
    @QueryMapping
    public Optional<Book> getBookById(@Argument Long id) {
        return bookRepository.findById(id);
    }

    // Mutation: Add a new book
    @MutationMapping
    public Book addBook(@Argument String title, @Argument String author, @Argument Double price) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(price);
        return bookRepository.save(book);
    }

    // Mutation: Update an existing book
    @MutationMapping
    public Book updateBook(
            @Argument Long id,
            @Argument String title,
            @Argument String author,
            @Argument Double price
    ) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (title != null) book.setTitle(title);
            if (author != null) book.setAuthor(author);
            if (price != null) book.setPrice(price);
            return bookRepository.save(book);
        }
        throw new RuntimeException("Book not found");
    }

    // Mutation: Delete a book
    @MutationMapping
    public boolean deleteBook(@Argument Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

