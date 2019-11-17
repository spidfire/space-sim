package nl.djurredeboer.sbs.space.ship.simulator.core

import nl.djurredeboer.sbs.space.ship.simulator.communication.User
import nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties.GameObject
import nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties.MovingObjectInterfaceInterface
import nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties.StaticObjectInterface
import nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties.SteerInterface
import nl.djurredeboer.sbs.space.ship.simulator.ships.BasicShip
import nl.djurredeboer.sbs.space.ship.simulator.units.Coordinate
import nl.djurredeboer.sbs.space.ship.simulator.units.Velocity

class ObjectManager {
    var gameObjects = mutableListOf<GameObject>()
    var namedObjects = mutableMapOf<String, GameObject>()

    fun getInitialState() {

        println("Starting state")
        val shipA = BasicShip()
        shipA.velocity = Velocity(0.0, 3.0)

        val shipB = BasicShip()

        shipB.velocity = Velocity(0.0, 3.0)
        shipB.targetDirection = Math.PI


        gameObjects.add(shipA)
        gameObjects.add(shipB)

        namedObjects.put("Pilot one", shipA)


    }

    fun doOneStep() {
        for (gameObject in gameObjects) {
            if (gameObject is SteerInterface) {
                gameObject.updateSteerDirection()
            }
            if (gameObject is MovingObjectInterfaceInterface) {
                gameObject.moveObject()
            }

        }
    }

    fun getObjectByName(user: User): GameObject? {
        return namedObjects.get(user.name)
    }

    fun getObjectsNear(position: Coordinate, viewRadius: Double): List<GameObject> {
        return gameObjects.filter {
            if (it is StaticObjectInterface) {
                val distance = position.distance(it.position)
                distance < viewRadius
            } else {
                false
            }
        }
    }


}