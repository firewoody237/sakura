package com.example.sakura.integrated.db.dto

import com.example.sakura.integrated.db.model.User

data class ReplyMessageDTO(
    val senderId: Long = 0L,
    val receiverId: Long = 0L,
    val originalMessageId: Long = 0L,
    val content: String?,
)
