package com.example.repository;

import com.example.entity.Message;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value="Select * from message where posted_by",nativeQuery = true)
    List<Message> findByPosted_by(@Param("pos_id")Integer posted_by);

    @Modifying
    @Transactional

    @Query(value="DELETE FROM Message WHERE message_id=?1", nativeQuery = true)
    int deleteMessageTotal(int message_id); 

    
}
