package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        // Implemet message creation logic based on user stories 
        // Validate input, link to existing user, save to the database, and return the saved message 
        // Handle validation and expectations appopriately 
        
        if (!accountRepository.existsById(message.getPosted_by())){
            return null;
        }



        else if (message.getMessage_text() == null || message.getMessage_text().isBlank() || message.getMessage_text().length()  > 255) {
            return null; 
        }
        
        return messageRepository.save(message);
    
    }




    



    public List<Message> getAllMessages(Integer account_id) {
        if (account_id != null) {
            // Retrieve messages for a specific user
            return messageRepository.findByPosted_by(account_id);
        } else {
            // Retrieve all messages
            return messageRepository.findAll();
        }
    }
    
    



    public Message getMessageById(Integer message_id) {
        Optional<Message> existingMessageOptional = messageRepository.findById(message_id);
        return existingMessageOptional.orElse(null);
    }








  

    public int  deleteMessageById(int message_id) {
        Optional<Message> possibleMessage = messageRepository.findById(message_id);
        if (possibleMessage.isPresent()) { 
            int numDelete = messageRepository. deleteMessageTotal(message_id);
            System.out.println("Test");
            System.out.println(numDelete);
            return numDelete;
        }
            return 0;
            
    }

    
    
    public int updateMessage(Integer message_id, String newMessageText) {
        Message existingMessage = getMessageById(message_id);
    
        if (existingMessage != null) {
            if (newMessageText == null || newMessageText.isEmpty()) {
                return 0; // Indicate that no rows were modified for empty message text
            }
    
            if (newMessageText.length() > 255) {
                return 0; // Indicate that no rows were modified for overly long message text
            }
    
            existingMessage.setMessage_text(newMessageText);
            messageRepository.save(existingMessage); // Save the updated message
            return 1; // Indicate that one row was modified
        }
    
        return 0; // Indicate that no rows were modified (message not found)
    }
    

    public List<Message> getMessagesByAccount(Integer posted_by) {
        return messageRepository.findByPosted_by(posted_by);
    }

    
}
