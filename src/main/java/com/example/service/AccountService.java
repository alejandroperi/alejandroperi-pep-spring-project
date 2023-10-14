package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) throws Exception{
        // Implement registratin logic based on user stories 
        // Validate input, check for existing username, save to database, and return the saved account
        // Handle calidation and exeptiond appropriately 

        // Check if the username in not blank and password ia at leat 4 characters long 
        if (account.getUsername() == null || account.getUsername().isBlank() || account.getPassword() == null || account.getPassword().length() < 4) {
            // Handle validation failure 
            // You can throw an exception or return null as per your project requirements 
            
            return null; 
        }
        // Check if the username already exists 
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null ) {
            // Handle duplicate username
            // You can throw an exeption or return null as per your project requirements 
            throw new Exception("the acoount can not have duplicate username");
            //return null; }
        }
        // save the new account to the database 
        return accountRepository.save(account);
        }    
        public Account login(String username, String password) {
            Account account = accountRepository.findByUsername(username);
            if (account != null && account.getPassword().equals(password)) {
                return account; // Login successful
            } else {
                return null; // Login failed
            }
        }
    
        public Account findByUsername(String username) {
            return accountRepository.findByUsername(username);
        }
    


        }

    
    

