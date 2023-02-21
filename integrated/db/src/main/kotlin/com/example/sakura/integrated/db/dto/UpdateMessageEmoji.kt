package com.example.sakura.integrated.db.dto

import com.example.sakura.integrated.db.enum.Emoji

data class UpdateMessageEmoji(
    val id: Long = 0L,
    val receiverId: Long = 0L,
    val emoji: Emoji = Emoji.NONE
)
