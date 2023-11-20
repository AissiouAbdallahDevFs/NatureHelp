package com.NatureHelp.api.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.NatureHelp.api.Model.Roles;
import com.NatureHelp.api.Model.User;
import com.NatureHelp.api.Repository.RolesRepository;
import com.NatureHelp.api.Repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

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
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setState(new Date().toString());
            Long roleId = user.getRoles();

            Roles role = rolesRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role introuvable"));

            user.setRole(role);
            User savedUser = userRepository.save(user);
            return savedUser;
        }
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
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setCity(updatedUser.getCity());
            existingUser.setZip(updatedUser.getZip());
            existingUser.setCountry(updatedUser.getCountry());
            existingUser.setState(updatedUser.getState());
            
            User updatedRecord = userRepository.save(existingUser);
            return updatedRecord;
        } else {
            throw new NotFoundException("Enregistrement introuvable");
        }
    }

    public class AuthenticationResponse {
        private String token;
    
        public AuthenticationResponse(String token) {
            this.token = token;
        }
    
        public String getToken() {
            return token;
        }
    
        public void setToken(String token) {
            this.token = token;
        }
    }
    
    public AuthenticationResponse authenticate(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                String token = jwtTokenService.generateJwtToken(email, password);
                return new AuthenticationResponse(token);
            }
        }

        throw new RuntimeException("Email ou mot de passe incorrect");
    }
    
    

}
