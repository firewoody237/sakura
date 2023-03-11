package com.example.sakura.integrated.db.dto

import com.example.sakura.integrated.db.enum.Emoji

data class UpdateMessageEmoji(
    val id: Long?,
    val receiverId: Long?,
    val emoji: Emoji = Emoji.NONE
)
