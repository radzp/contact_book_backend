package com.amw.contact_book_backend.auth;


import com.amw.contact_book_backend.exception.EmailAlreadyExistsException;
import com.amw.contact_book_backend.jwt.JwtService;
import com.amw.contact_book_backend.user.Role;
import com.amw.contact_book_backend.user.User;
import com.amw.contact_book_backend.user.UserRepository;
import com.amw.contact_book_backend.user.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    public AuthenticationResponse register(RegisterRequest registerRequest) {
        try {
            User user = User.builder()
                    .name(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            String token = jwtService.generateToken(user);
            return new AuthenticationResponse(token);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException("User with this email already exists");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtService.generateToken(user);

        Date jwtIssuedAt = jwtService.extractClaim(token, Claims::getIssuedAt);
        Date jwtExpiration = jwtService.extractClaim(token, Claims::getExpiration);
        long jwtDuration = (jwtExpiration.getTime() - jwtIssuedAt.getTime()) / 1000;

        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setHttpOnly(true); // zabezpieczenie przed atakami XSS
        jwtCookie.setMaxAge((int) jwtDuration); // czas życia ciasteczka
        jwtCookie.setPath("/"); // "/", oznacza, że ciasteczko będzie dostępne dla całej strony

        // dodanie ciasteczka do odpowiedzi
        response.addCookie(jwtCookie);

        return new AuthenticationResponse(token);
    }


    public boolean checkAuthentication(HttpServletRequest request) {
        Cookie jwtCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "jwtToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null);

        if(jwtCookie != null){
            String jwtToken = jwtCookie.getValue();
            String userEmail = jwtService.extractUsername(jwtToken);

            if (userEmail != null){
                UserDetails userDetails = userService.loadUserByUsername(userEmail);
                return jwtService.isTokenValid(jwtToken, userDetails);
            }
        }

        return false;
    }

}