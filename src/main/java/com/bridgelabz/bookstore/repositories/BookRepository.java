package com.bridgelabz.bookstore.repositories;

import com.bridgelabz.bookstore.models.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository interface for Book Database Operations
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-14
 * @see {@link JpaRepository } for Database Operations
 */
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> { }
