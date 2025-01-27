package com.yourcaryourway.chatappback.repositories;

import com.yourcaryourway.chatappback.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository  extends JpaRepository<Chat, Long> {
    List<Chat> findAllByUserId(Long id);
    List<Chat> findAllByStatus(String status);

}
