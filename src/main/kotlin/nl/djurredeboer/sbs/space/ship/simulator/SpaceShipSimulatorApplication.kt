package nl.djurredeboer.sbs.space.ship.simulator

import nl.djurredeboer.sbs.space.ship.simulator.communication.ChatHandler
import nl.djurredeboer.sbs.space.ship.simulator.core.GameLoop
import nl.djurredeboer.sbs.space.ship.simulator.core.ObjectManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry


val objectManager = ObjectManager()

@Configuration
@EnableWebSocket
class WSConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(ChatHandler(objectManager), "/chat").withSockJS()
    }
}


@SpringBootApplication
class ChatApplication {

    init {

        objectManager.getInitialState()
        val gameLoop = GameLoop(objectManager)
        gameLoop.start()
    }
}

fun main(args: Array<String>) {
    runApplication<ChatApplication>(*args)
}