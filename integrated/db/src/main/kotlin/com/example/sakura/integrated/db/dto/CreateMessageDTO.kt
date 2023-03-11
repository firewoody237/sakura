package com.example.sakura.integrated.db.dto

import com.example.sakura.integrated.db.model.User

data class CreateMessageDTO(
    val senderId: Long?,
    val receiverId: Long?,
    val title: String?,
    val content: String?,
)
