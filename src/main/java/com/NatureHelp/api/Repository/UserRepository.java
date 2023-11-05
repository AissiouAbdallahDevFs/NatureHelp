package com.NatureHelp.api.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.NatureHelp.api.Model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	 Optional<User> findByEmail(String email);
}

