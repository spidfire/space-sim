package nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties

import nl.djurredeboer.sbs.space.ship.simulator.units.Coordinate

interface StaticObjectInterface : GameObject {
    var position: Coordinate
}