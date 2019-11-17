package nl.djurredeboer.sbs.space.ship.simulator.properties.objectproperties

import kotlin.math.min

interface DirectionBasedSteering : SteerInterface {

    var targetDirection: Double?
    var rotationSpeed: Double

    override fun updateSteerDirection() {
        val direction = targetDirection
        if (direction != null) {
            val current = (velocity.direction / Math.PI) * 180
            val target = (direction / Math.PI) * 180.0
            val targetAngleDegrees = (target - current + 540.0).rem(360.0) - 180.0

            val targetAngle = (targetAngleDegrees / 180.0) * Math.PI
            if (targetAngle < 0) {
                velocity = velocity.copy(direction = velocity.direction - min(rotationSpeed, -targetAngle))
            } else {
                velocity = velocity.copy(direction = velocity.direction + min(rotationSpeed, targetAngle))
            }

            if (targetAngle > 0.01 && targetAngle < 0.01) {
                targetDirection = null
            }

        }

    }


}