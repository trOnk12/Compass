package com.example.compass.utills

import java.util.*

class MovingAverageStrategy : SignalSmoothingStrategy {

    var sum: Double = 0.00
    var size: Int = 5
    var list: LinkedList<Float> = LinkedList()

    override fun compute(data: Float): Double {
        sum += data
        list.offer(data)

        if (list.size <= size) {
            return sum / list.size
        }

        sum -= list.poll()

        return Math.toDegrees(sum / size)
    }

}