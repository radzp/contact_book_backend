package com.amw.contact_book_backend.dbUtils;

import com.amw.contact_book_backend.user.Role;
import com.amw.contact_book_backend.user.User;
import com.amw.contact_book_backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitialDatabaseLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("ania@ania.com").isEmpty()) {
            User ania = new User();
            ania.setName("ania");
            ania.setPassword(passwordEncoder.encode("ania"));
            ania.setRole(Role.USER);
            ania.setEmail("ania@ania.com");
            userRepository.save(ania);
        }
        if (userRepository.findByEmail("marcin@marcin.com").isEmpty()) {
            User marcin = new User();
            marcin.setName("marcin");
            marcin.setPassword(passwordEncoder.encode("marcin"));
            marcin.setRole(Role.USER);
            marcin.setEmail("marcin@marcin.com");
            userRepository.save(marcin);
        }

    }
}