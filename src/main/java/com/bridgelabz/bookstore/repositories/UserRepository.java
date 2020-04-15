package com.bridgelabz.bookstore.repositories;

import com.bridgelabz.bookstore.models.Address;
import com.bridgelabz.bookstore.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * JPA Repository interface for User Database Operations
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-12
 * @see {@link JpaRepository } for Database Operations
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findOneByUserName( final String userName );

    boolean existsByUserName( final String userName );

    @Modifying
    @Transactional
    @Query(value = " update users set is_verified = true where user_name =  ? ", nativeQuery = true)
    void verifyTheUser( final String userName );
    
}
