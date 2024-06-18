package com.amw.contact_book_backend.contact;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepo extends MongoRepository<Contact, String> {
    Optional<Contact> findById(String id);
}