package nl.djurredeboer.sbs.space.ship.simulator.ships

import nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties.DirectionBasedSteering
import nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties.MovingObjectInterfaceInterface
import nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties.PilotViewProperty
import nl.djurredeboer.sbs.space.ship.simulator.units.Coordinate
import nl.djurredeboer.sbs.space.ship.simulator.units.Velocity

class BasicShip : MovingObjectInterfaceInterface, DirectionBasedSteering, PilotViewProperty {
    override var velocity: Velocity = Velocity(0.0, 0.0)
    override var position: Coordinate = Coordinate(0.0, 0.0)
    override var targetDirection: Double? = null
    override var rotationSpeed: Double = Math.PI / 180
    override val viewDistance: Double = 100.0
}