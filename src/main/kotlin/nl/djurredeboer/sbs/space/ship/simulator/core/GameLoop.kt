package nl.djurredeboer.sbs.space.ship.simulator.core

class GameLoop(val objectManager: ObjectManager) {

    fun start() {
        Thread {
            while (true) {
                objectManager.doOneStep()
                Thread.sleep(500)
            }
        }.start()
    }


}