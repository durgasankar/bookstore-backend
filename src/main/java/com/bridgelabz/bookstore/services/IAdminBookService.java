package com.bridgelabz.bookstore.services;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.models.BookEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAdminBookService {

    boolean isBookAddedToStoreByAdmin( BookDto bookDto, String token );

    List<BookEntity> getAllBooksFromStore( String token );

    boolean isRemovedFromStoreByAdmin( long bookId, String token );
}
