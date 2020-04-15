package com.bridgelabz.bookstore.repositories;

import com.bridgelabz.bookstore.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * JPA Repository interface for User's address Database Operations
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-15
 * @see {@link JpaRepository } for Database Operations
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from users_addresses where address_id = ? and user_id = ?", nativeQuery = true)
    void removeAddress( String addressId, long userId );
}
