package taskTwo

import util.readPackageLocale

object Task {
    private val inputs = readPackageLocale<Task>("input.txt")
        .map {
            val (a, b) = it.split(" ")
            a to b.toInt()
        }

    fun partOne() {
        inputs
            .groupBy(Pair<String, Int>::first, Pair<String, Int>::second)
            .let {
                val forward = it["forward"]?.sum() ?: 0
                val depth = (it["down"]?.sum() ?: 0) - (it["up"]?.sum() ?: 0)

                forward * depth
            }
            .also(::println)
    }

    fun partTwo() {
        var forward = 0
        var depth = 0
        var aim = 0

        for (input in inputs) {
            val (direction, value) = input
            when (direction) {
                "forward" -> {
                    forward += value
                    depth += value * aim
                }
                "down" -> {
                    aim += value
                }
                "up" -> {
                    aim -= value
                }
            }
        }

        println(forward * depth)
    }

}