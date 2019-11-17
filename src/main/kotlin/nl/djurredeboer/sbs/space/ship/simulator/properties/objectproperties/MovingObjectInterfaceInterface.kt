package nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties

import nl.djurredeboer.sbs.space.ship.simulator.units.Velocity

interface MovingObjectInterfaceInterface : StaticObjectInterface {
    var velocity: Velocity

    fun moveObject() {
        position = position.addVelocity(velocity)
    }

}