package com.bridgelabz.bookstore.repositories;

import com.bridgelabz.bookstore.models.AdminBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository interface for Book Database Operations
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-16
 * @see {@link JpaRepository } for Database Operations
 */
@Repository
public interface AdminBookRepository extends JpaRepository<AdminBookEntity, Long> {

    Optional<AdminBookEntity> findOneByBookCode( final String bookCode );
}
