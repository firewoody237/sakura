package com.example.sakura.api.controller

import com.example.sakura.integrated.db.enum.Emoji

data class MessageVO(
    val id: Long,
    val content: String,
    val readYn: Boolean,
    val emoji: Emoji,
    val senderId: Long,
    val receiverId: Long,
    val originalMessageId: Long
)
