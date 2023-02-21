package com.example.sakura.integrated.db.dto

import com.example.sakura.integrated.db.model.User

data class CreateMessageDTO(
    val senderId: Long = 0L,
    val receiverId: Long = 0L,
    val title: String?,
    val content: String?,
)
