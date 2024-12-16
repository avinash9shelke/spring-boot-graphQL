package com.example.graphql.repository;

/**
 * @author avinash
 */

import com.example.graphql.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
