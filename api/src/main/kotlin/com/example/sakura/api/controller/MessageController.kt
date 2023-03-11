package com.example.sakura.api.controller

import com.example.sakura.integrated.db.dto.*
import com.example.sakura.integrated.db.entity.Message
import com.example.sakura.integrated.db.service.MessageService
import com.example.sakura.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class MessageController(
    private val messageService: MessageService
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/message", method = [RequestMethod.POST])
    fun createMessage(@RequestBody createMessageDTO: CreateMessageDTO): MessageVO {
        log.debug("createMessage, createMessageDTO = '$createMessageDTO'")

        val message = messageService.createMessage(createMessageDTO)
        return MessageVO(
            id = message.id,
            content = message.content!!,
            readYn = message.readYn,
            emoji = message.emoji,
            senderId = message.senderId,
            receiverId = message.receiverId,
            originalMessageId = message.originalMessageId
        )
    }

    @ApiRequestMapping("/message/{id}", method = [RequestMethod.GET])
    fun getMessage(@PathVariable id: Long): MessageVO {
        log.debug("getMessage, id = '$id'")

        val message = messageService.getMessage(id)
        return MessageVO(
            id = message.id,
            content = message.content!!,
            readYn = message.readYn,
            emoji = message.emoji,
            senderId = message.senderId,
            receiverId = message.receiverId,
            originalMessageId = message.originalMessageId
        )
    }

    @ApiRequestMapping("/message", method = [RequestMethod.PATCH])
    fun readMessage(@RequestBody readMessageDTO: ReadMessageDTO): MessageVO {
        log.debug("readMessage, readMessageDTO = '$readMessageDTO'")

        val message = messageService.readMessage(readMessageDTO)
        return MessageVO(
            id = message.id,
            content = message.content!!,
            readYn = message.readYn,
            emoji = message.emoji,
            senderId = message.senderId,
            receiverId = message.receiverId,
            originalMessageId = message.originalMessageId
        )
    }

    @ApiRequestMapping("/message", method = [RequestMethod.GET])
    fun getMessages(@RequestBody getMessagesDTO: GetMessagesDTO): MutableList<MessageVO> {
        log.debug("getMessages, getMessagesDTO = '$getMessagesDTO'")

        return messageService.getMessagesByUserId(getMessagesDTO).map {
            message -> MessageVO(
                id = message.id,
                content = message.content!!,
                readYn = message.readYn,
                emoji = message.emoji,
                senderId = message.senderId,
                receiverId = message.receiverId,
                originalMessageId = message.originalMessageId
            )
        }.toMutableList()
    }

    @ApiRequestMapping("/message/reply", method = [RequestMethod.PUT])
    fun replyMessage(@RequestBody replyMessageDTO: ReplyMessageDTO): MessageVO {
        log.debug("replyMessage, replyMessageDTO = '$replyMessageDTO'")

        val message = messageService.replyMessage(replyMessageDTO)
        return  MessageVO(
            id = message.id,
            content = message.content!!,
            readYn = message.readYn,
            emoji = message.emoji,
            senderId = message.senderId,
            receiverId = message.receiverId,
            originalMessageId = message.originalMessageId
        )
    }

    @ApiRequestMapping("/message/emoji", method = [RequestMethod.PUT])
    fun updateEmoji(@RequestBody updateMessageEmoji: UpdateMessageEmoji) {
        log.debug("updateEmoji, updateMessageEmoji = '$updateMessageEmoji'")

        messageService.updateMessageEmoji(updateMessageEmoji)
    }
}