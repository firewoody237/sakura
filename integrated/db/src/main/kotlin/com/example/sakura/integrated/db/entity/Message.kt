package com.example.sakura.integrated.db.entity

import com.example.sakura.integrated.db.BaseTime
import com.example.sakura.integrated.db.enum.Emoji
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
@Table(name = "message")
data class Message(
    @Id
    @GeneratedValue
    val id: Long = 0L,

    @Column(nullable = false, length = 10000)
    var content: String? = "",

    @Column
    var readYn: Boolean = false,

    @Column
    @Enumerated(EnumType.STRING)
    var emoji: Emoji = Emoji.NONE,

    @Column
    var senderId: Long = 0L,

    @Column
    var receiverId: Long = 0L,
    @Column
    var originalMessageId: Long = 0L,
): BaseTime() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Message(id=$id, content=${content}, senderId=${senderId}, receiverId=${receiverId}"
    }
}
