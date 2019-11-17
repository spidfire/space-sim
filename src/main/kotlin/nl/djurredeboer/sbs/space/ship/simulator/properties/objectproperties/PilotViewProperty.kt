package nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties

import nl.djurredeboer.sbs.space.ship.simulator.core.ObjectManager
import nl.djurredeboer.sbs.space.ship.simulator.units.Coordinate

interface PilotViewProperty {

    val viewDistance: Double

    data class PilotViewItems(val relativePosition: Coordinate)

    fun getPilotView(objectManager: ObjectManager): List<PilotViewItems> {

        return if (this is StaticObjectInterface) {
            objectManager
                    .getObjectsNear(this.position, viewDistance)
                    .filter { it != this@PilotViewProperty }
                    .filterIsInstance(StaticObjectInterface::class.java)
                    .map {

                        PilotViewItems(this.position.relativePosition(it.position, viewDistance))
                    }
        } else {
            emptyList()
        }

    }


}