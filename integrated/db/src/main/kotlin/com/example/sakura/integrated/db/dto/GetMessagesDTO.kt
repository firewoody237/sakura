package com.example.sakura.integrated.db.dto

data class GetMessagesDTO(
    val userId: Long?,
    val senderOrReceiver: String = "S"
)
