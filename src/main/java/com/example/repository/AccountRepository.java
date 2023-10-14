package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AccountRepository extends JpaRepository<Account, Integer> {
    // Find an account by username
    Account findByUsername(String username);

    // Find an account by username and password
    Account findByUsernameAndPassword(String username, String password);
}
