package com.bridgelabz.bookstore.services.impl;

import com.bridgelabz.bookstore.repositories.BookRepository;
import com.bridgelabz.bookstore.repositories.UserRepository;
import com.bridgelabz.bookstore.services.IAdminBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminBookServiceImplementation implements IAdminBookService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

}
