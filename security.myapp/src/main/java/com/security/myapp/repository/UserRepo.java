package com.security.myapp.repository;

import com.security.myapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    @Query("select user from User user where email =?1")
    Optional<User> findByEmail(String email);
}
