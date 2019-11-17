package nl.djurredeboer.sbs.space.ship.simulator.units

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Coordinate(val x: Double, val y: Double) {
    fun addVelocity(velocity: Velocity): Coordinate {
        return Coordinate(
                x + (velocity.speed * cos(velocity.direction)),
                y + (velocity.speed * sin(velocity.direction))
        )
    }

    fun distance(position: Coordinate): Double {
        return sqrt((position.x - this.x) * (position.x - this.x) + (position.y - this.y) * (position.y - this.y))
    }

    fun relativePosition(it: Coordinate, viewDistance: Double): Coordinate {
        return Coordinate(
                (this.x - it.x) / viewDistance,
                (this.y - it.y) / viewDistance
        )
    }
}