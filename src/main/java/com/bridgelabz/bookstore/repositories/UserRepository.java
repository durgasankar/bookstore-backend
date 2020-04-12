package com.bridgelabz.bookstore.repositories;

import com.bridgelabz.bookstore.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository interface for User Database Operations
 *
 * @author Durgasankar Mishra
 * @created 2020-04-12
 * @version 1.0
 * @see {@link JpaRepository} for Database Operations
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findOneByUserName( String userName );

    boolean existsByUserName( String userName );
}
