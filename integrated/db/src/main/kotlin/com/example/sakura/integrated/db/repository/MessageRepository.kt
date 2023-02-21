package com.example.sakura.integrated.db.repository

import com.example.sakura.integrated.db.entity.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: JpaRepository<Message, Long> {
    fun getMessagesBySenderId(senderId: Long): MutableList<Message>
    fun getMessagesByReceiverId(receiverId: Long): MutableList<Message>
}