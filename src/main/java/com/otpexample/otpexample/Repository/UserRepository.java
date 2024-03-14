package com.otpexample.otpexample.Repository;


import com.otpexample.otpexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
