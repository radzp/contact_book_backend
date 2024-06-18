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
    @NotNull(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Email cannot be empty")
    @Email
    private String email;
    @NotNull(message = "Title cannot be empty")
    private String title;
    @NotNull(message = "Phone cannot be empty")
    private String phone;
    @NotNull(message = "Address cannot be empty")
    private String address;
    @NotNull(message = "Status cannot be empty")
    private String status;
    private String photoUrl;
}