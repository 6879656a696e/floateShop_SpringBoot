package com.woori.demo.repository;

import com.woori.demo.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository  extends JpaRepository<Chat, Long> {
}
