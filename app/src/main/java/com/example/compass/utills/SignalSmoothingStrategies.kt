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

class LowPassFilterStrategy : SignalSmoothingStrategy {

    private var sumSin: Float = 0.toFloat()
    private var sumCos: Float = 0.toFloat()

    private val queue = ArrayDeque<Float>()

    private fun add(radians: Float) {
        sumSin += Math.sin(radians.toDouble()).toFloat()
        sumCos += Math.cos(radians.toDouble()).toFloat()

        queue.add(radians)

        val length = 15

        if (queue.size > length) {
            val old = queue.poll()!!
            sumSin -= Math.sin(old.toDouble()).toFloat()
            sumCos -= Math.cos(old.toDouble()).toFloat()
        }
    }

    private fun average(): Float {
        val size = queue.size

        return Math.atan2((sumSin / size).toDouble(), (sumCos / size).toDouble()).toFloat()
    }

    override fun compute(data: Float): Double {
        add(data)

        return Math.toDegrees(average().toDouble())
    }
}

