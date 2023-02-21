package com.example.sakura.integrated.db.dto

import com.example.sakura.integrated.db.model.User

data class ReadMessageDTO(
    val readerId: Long = 0L,
    val id: Long = 0L,
)
