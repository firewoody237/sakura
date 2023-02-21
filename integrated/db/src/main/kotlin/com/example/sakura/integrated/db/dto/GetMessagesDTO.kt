package com.example.sakura.integrated.db.dto

data class GetMessagesDTO(
    val userId: Long = 0L,
    val senderOrReceiver: String = "S"
)
