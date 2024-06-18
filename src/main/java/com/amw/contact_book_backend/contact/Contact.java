package com.amw.contact_book_backend.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Document(collection = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
public class Contact {
    @Id
    private String id; // primary key
    @NotNull
    private String name;
    @Email
    private String email;
    @NotNull
    private String title;
    @NotNull
    private String phone;
    @NotNull
    private String address;
    private String status;
    private String photoUrl;
}