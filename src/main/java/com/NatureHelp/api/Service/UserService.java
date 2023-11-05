package com.NatureHelp.api.Service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.NatureHelp.api.Model.User;
import com.NatureHelp.api.Repository.UserRepository;
import com.NatureHelp.api.Config.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    UserRepository userRepository;

    public class NotFoundException extends RuntimeException implements Serializable {
        private static final long serialVersionUID = 1L;

        public NotFoundException(String message) {
            super(message);
        }
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> GetUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
    	user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            userRepository.delete(existingUser);
        } else {
            throw new NotFoundException("Enregistrement introuvable");
        }
    }

    public User updatePassword(User updatePassword) {
        User existingUser = userRepository.findById(updatePassword.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setPassword(updatePassword.getPassword());
        }
        User updatedRecord = userRepository.save(existingUser);
        return updatedRecord;
    }

    public User updateUser(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setfirstname(updatedUser.getfirstname());
            existingUser.setEmail(updatedUser.getEmail());
            User updatedRecord = userRepository.save(existingUser);
            return updatedRecord;
        } else {
            throw new NotFoundException("Enregistrement introuvable");
        }
    }

    public String authenticate(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                byte[] jwtSecretBytes = jwtConfig.getJwtSecret().getBytes();

                String token = Jwts.builder()
                        .setSubject(email)
                        .signWith(SignatureAlgorithm.HS256, jwtSecretBytes)
                        .compact();
                return "{\"token\": \"" + token + "\"}";
            }
        }

        return null;
    }

    public String getEmailFromToken(String token) {
        byte[] jwtSecretBytes = jwtConfig.getJwtSecret().getBytes();

        String email = Jwts.parser()
                .setSigningKey(jwtSecretBytes)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return email;
    }

    public Optional<User> GetUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
