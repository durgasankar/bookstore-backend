package com.bridgelabz.bookstore.services;

import com.bridgelabz.bookstore.dto.BookDto;
import org.springframework.stereotype.Service;

@Service
public interface IAdminBookService {

    boolean isBookAddedToStore( BookDto bookDto, String token );

}
