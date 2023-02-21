package com.example.sakura.integrated.db.model

import com.example.sakura.integrated.db.enum.Category
import java.time.LocalDateTime

data class Post(
    val id: Long = 0L,
    var title: String = "",
    var content: String = "",
    var category: Category = Category.UNCATEGORIZED,
    val authorId: Long = 0L,
    var deletedAt: LocalDateTime? = null,
)
