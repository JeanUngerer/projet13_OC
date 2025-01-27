package com.yourcaryourway.chatappback.repositories;

import com.yourcaryourway.chatappback.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
