package com.example.controller;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@EnableAutoConfiguration
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;
    private final MessageRepository messageRepository; // Inject the MessageRepository
    private final AccountRepository accountRepository; // Inject the AccountRepository

    @Autowired
    public SocialMediaController(
        AccountService accountService,
        MessageService messageService,
        MessageRepository messageRepository,
        AccountRepository accountRepository // Inject the AccountRepository
    ) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.messageRepository = messageRepository; // Initialize the MessageRepository
        this.accountRepository = accountRepository; // Initialize the AccountRepository
    }
    @PostMapping("/register")
public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
    try {Account newAccount = accountService.registerAccount(account);

    if (newAccount != null) {
        return new ResponseEntity<Account>(newAccount, HttpStatus.OK);
    } else {
        return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
    }
}
catch(Exception e){
    return new ResponseEntity<Account>(HttpStatus.CONFLICT);
}
}

@PostMapping("/login")
public ResponseEntity<Account> login(@RequestBody Account userAccount) {
    Account loggedInAccount = accountService.login(userAccount.getUsername(), userAccount.getPassword());

    if (loggedInAccount != null) {
        return new ResponseEntity<Account>(loggedInAccount, HttpStatus.OK); // Return 200 on successful login
    } else {
        return new ResponseEntity<Account>(HttpStatus.UNAUTHORIZED); // Return 401 on failed login
    }
}



    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        if (newMessage != null) {
            return new ResponseEntity<Message>(newMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
        }
    }

















    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages(null);
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }




    @GetMapping("/messages/{message_id}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer message_id) {
        Message message = messageService.getMessageById(message_id);
    
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            // If the message is not found, return a 200 status with an empty response body
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    














    @DeleteMapping("/messages/{message_id}")
public ResponseEntity<Integer> deleteMessage(@PathVariable Integer message_id) {
    int rowsDeleted = messageService.deleteMessageById(message_id);
    return new ResponseEntity<>(rowsDeleted, HttpStatus.OK);
}





    
    @PatchMapping("/messages/{message_id}")
public ResponseEntity<Integer> updateMessage(@PathVariable Integer message_id, @RequestBody Map<String, String> messageText) {
    Optional<Message> existingMessageOptional = messageRepository.findById(message_id);

    if (!existingMessageOptional.isPresent()) {
        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST); // Return 400 if the message is not found
    }

    String newMessageText = messageText.get("message_text");

    if (newMessageText == null || newMessageText.isEmpty()) {
        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST); // Return 400 Bad Request for empty message text
    }

    if (newMessageText.length() > 255) {
        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST); // Return 400 Bad Request for overly long message text
    }

    int rowsModified = messageService.updateMessage(message_id, newMessageText);
    if (rowsModified > 0) {
        return new ResponseEntity<>(rowsModified, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 if something unexpected happened
    }
}




    
    

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable Integer account_id) {
        List<Message> messages = messageService.getMessagesByAccount(account_id);
        return new ResponseEntity<>(messages, HttpStatus.OK);
}

}