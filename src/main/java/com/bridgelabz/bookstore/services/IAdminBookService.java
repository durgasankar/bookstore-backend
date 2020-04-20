package com.bridgelabz.bookstore.services;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.models.AdminBookEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface IAdminBookService {

    boolean isBookAddedToStoreByAdmin( BookDto bookDto, String token );

    List<AdminBookEntity> getAllBooksFromStore( String token );

    boolean isRemovedFromStoreByAdmin( long bookId, String token );
}
