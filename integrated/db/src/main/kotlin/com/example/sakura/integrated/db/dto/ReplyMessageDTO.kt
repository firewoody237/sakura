package com.example.sakura.integrated.db.dto

import com.example.sakura.integrated.db.model.User

data class ReplyMessageDTO(
    val senderId: Long?,
    val receiverId: Long?,
    val originalMessageId: Long?,
    val content: String?,
)
