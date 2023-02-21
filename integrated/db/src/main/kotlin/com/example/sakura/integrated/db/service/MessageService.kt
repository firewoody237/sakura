package com.example.sakura.integrated.db.service

import com.example.sakura.integrated.common.ResultCode
import com.example.sakura.integrated.common.ResultCodeException
import com.example.sakura.integrated.db.dto.*
import com.example.sakura.integrated.db.entity.Message
import com.example.sakura.integrated.db.enum.Emoji
import com.example.sakura.integrated.db.repository.MessageRepository
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val messageRepository: MessageRepository,
    private val userApiService: UserApiService,
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    //메시지 작성
    fun createMessage(createMessageDTO: CreateMessageDTO): Message {
        log.debug("call createMessage, createMessageDTO = '$createMessageDTO'")

        if (createMessageDTO.senderId == 0L) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [senderId]이 존재하지 않습니다."
            )
        }

        if (createMessageDTO.receiverId == 0L) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [receiverId]이 존재하지 않습니다."
            )
        }

        if (createMessageDTO.content.isNullOrEmpty()) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [content]이 존재하지 않습니다."
            )
        }

        val sender = userApiService.getUserById(createMessageDTO.senderId)
        val receiver = userApiService.getUserById(createMessageDTO.receiverId)

        return try {
            messageRepository.save(
                Message(
                    content = createMessageDTO.content,
                    readYn = false,
                    senderId = sender.id,
                    receiverId = receiver.id,
                    emoji = Emoji.NONE,
                    originalMessageId = 0L
                )
            )
        } catch (e: Exception) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.WARN,
                message = "createMessage 호출 중 DB오류 발생 : ${e.message}"
            )
        }
    }

    //단건 메시지 읽음
    fun readMessage(readMessageDTO: ReadMessageDTO): Message {
        log.debug("call readMessage, readMessageDTO = '$readMessageDTO'")

        if (readMessageDTO.id == 0L) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [id]이 존재하지 않습니다."
            )
        }

        if (readMessageDTO.readerId == 0L) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [readerId]이 존재하지 않습니다."
            )
        }

        val foundMessage = getMessage(readMessageDTO.id)

        if (foundMessage.receiverId != readMessageDTO.readerId) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_MESSAGE_READER_AND_RECEIVER_NOT_SAME,
                loglevel = Level.WARN,
                message = "게시글이 존재하지 않습니다."
            )
        }

        foundMessage.readYn = true
        return messageRepository.save(foundMessage)
    }

    fun getMessage(id: Long): Message {
        log.debug("call getMessage, id = '$id'")

        if (id == 0L) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [id]이 존재하지 않습니다."
            )
        }

        val optionalMessage = try {
            messageRepository.findById(id)
        } catch (e: Exception) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "readMessage 호출 중 DB오류 발생 : ${e.message}"
            )
        }

        return when (optionalMessage.isPresent) {
            true -> optionalMessage.get()
            else -> throw ResultCodeException(
                resultCode = ResultCode.ERROR_MESSAGE_NOT_EXIST,
                loglevel = Level.ERROR
            )
        }
    }

    fun getMessagesByUserId(getMessagesDTO: GetMessagesDTO): MutableList<Message> {
        log.debug("call getMessages, getMessagesDTO = '$getMessagesDTO'")

        if (getMessagesDTO.userId == 0L) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [userId]이 존재하지 않습니다."
            )
        }

        if (getMessagesDTO.senderOrReceiver.isEmpty()) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [senderOrReceiver]이 존재하지 않습니다."
            )
        }

        val messages = messageRepository.getMessagesBySenderId(getMessagesDTO.userId)

        when (messages.size) {
            0 -> throw ResultCodeException(
                resultCode = ResultCode.ERROR_MESSAGE_NOT_EXIST,
                loglevel = Level.WARN,
            )
            else -> return messages
        }
    }

    //메시지 답장
    fun replyMessage(replyMessageDTO: ReplyMessageDTO): Message {
        log.debug("call replyMessage, replyMessageDTO = '$replyMessageDTO'")

        if (replyMessageDTO.senderId == 0L) {
            throw ResultCodeException(
                ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.INFO,
                message = "파라미터에 [senderId]이 존재하지 않습니다."
            )
        }

        if (replyMessageDTO.receiverId == 0L) {
            throw ResultCodeException(
                ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.INFO,
                message = "파라미터에 [receiverId]이 존재하지 않습니다."
            )
        }

        if (replyMessageDTO.originalMessageId == 0L) {
            throw ResultCodeException(
                ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.INFO,
                message = "파라미터에 [originalMessageId]이 존재하지 않습니다."
            )
        }

        val sender = userApiService.getUserById(replyMessageDTO.senderId)
        val receiver = userApiService.getUserById(replyMessageDTO.receiverId)
        val originalMessage = getMessage(replyMessageDTO.originalMessageId)

        return try {
            messageRepository.save(
                Message(
                    content = replyMessageDTO.content,
                    readYn = false,
                    senderId = sender.id,
                    receiverId = receiver.id,
                    emoji = Emoji.NONE,
                    originalMessageId = originalMessage.id
                )
            )
        } catch (e: Exception) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.WARN,
                message = "replyMessage 호출 중 DB오류 발생 : ${e.message}"
            )
        }
    }

    fun updateMessageEmoji(updateMessageEmoji: UpdateMessageEmoji) {
        log.debug("call updateMessageEmoji, updateMessageEmoji = '$updateMessageEmoji'")

        if (updateMessageEmoji.id == 0L) {
            throw ResultCodeException(
                ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.INFO,
                message = "파라미터에 [id]이 존재하지 않습니다."
            )
        }

        if (updateMessageEmoji.receiverId == 0L) {
            throw ResultCodeException(
                ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.INFO,
                message = "파라미터에 [updateMessageEmoji]이 존재하지 않습니다."
            )
        }

        val foundMessage = getMessage(updateMessageEmoji.id)
        val foundUser = userApiService.getUserById(updateMessageEmoji.receiverId)

        if (foundMessage.receiverId != foundUser.id) {
            throw ResultCodeException(
                ResultCode.ERROR_MESSAGE_READER_AND_RECEIVER_NOT_SAME,
                loglevel = Level.INFO,
            )
        }

        foundMessage.emoji = updateMessageEmoji.emoji
        messageRepository.save(foundMessage)
    }
}