package nl.djurredeboer.sbs.space.ship.simulator.communication

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import nl.djurredeboer.sbs.space.ship.simulator.core.ObjectManager
import nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties.PilotViewProperty
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.atomic.AtomicLong

enum class UserType {
    PILOT
}

class User(val id: Long, val name: String, val type: UserType = UserType.PILOT)
class Message(val msgType: String, val data: Any)

class ChatHandler(objectManager: ObjectManager) : TextWebSocketHandler() {

    val sessionList = HashMap<WebSocketSession, User>()
    var uids = AtomicLong(0)

    init {
        Thread {
            while (true) {
                sessionList.forEach { webSocketSession, user ->
                    val item = objectManager.getObjectByName(user)
                    if (item is PilotViewProperty) {
                        val closeObjects = item.getPilotView(objectManager)
                        broadcast(Message("updateView", closeObjects))
                    }
                }
                Thread.sleep(500)
            }
        }.start()
    }

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList -= session
    }


    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val json = ObjectMapper().readTree(message.payload)
        // {type: "join/say", data: "name/msg"}
        when (json.get("type").asText()) {
            "join" -> {
                val user = User(uids.getAndIncrement(), json.get("data").asText())
                sessionList.put(session, user)
                // tell this user about all other users
//                emit(session, Message("users", sessionList.values))
                // tell all other users, about this user
                broadcastToOthers(session, Message("join", user))
            }
            "say" -> {
//                broadcast(Message("say", json.get("data").asText()))
            }
        }
    }

    fun emit(session: WebSocketSession, msg: Message) = session.sendMessage(TextMessage(jacksonObjectMapper().writeValueAsString(msg)))
    fun broadcast(msg: Message) = sessionList.forEach { emit(it.key, msg) }
    fun broadcastToOthers(me: WebSocketSession, msg: Message) = sessionList.filterNot { it.key == me }.forEach { emit(it.key, msg) }
}
