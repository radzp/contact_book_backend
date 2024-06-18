package com.amw.contact_book_backend.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotNull(message = "Email cannot be empty")
    @Email
    private String email;
    @NotNull(message = "Password cannot be empty")
    private String password;
}