package com.bridgelabz.bookstore.repositories;

import com.bridgelabz.bookstore.models.UserBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository interface for Book Database Operations of Users
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-14
 * @see {@link JpaRepository } for Database Operations
 */
@Repository
public interface UsersBookRepository extends JpaRepository<UserBookEntity, Long> {

    Optional<UserBookEntity> findOneByBookCode( final String bookCode );
}
