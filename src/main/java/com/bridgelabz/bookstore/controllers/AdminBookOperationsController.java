package com.bridgelabz.bookstore.controllers;

import com.bridgelabz.bookstore.services.IAdminBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminBookOperationsController {

    @Autowired
    private IAdminBookService adminBookService;
}
