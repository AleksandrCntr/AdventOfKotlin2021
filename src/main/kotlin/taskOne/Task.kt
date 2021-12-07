package taskOne

import util.readPackageLocale

object Task {
    private val numbers = readPackageLocale<Task>("input.txt")
        .map(String::toInt)

    fun partOne() {
        var changedCount = 0
        for (i in 1..numbers.lastIndex) {
            if (numbers[i] > numbers[i-1])
                changedCount++
        }

        val changedCount2 = numbers
            .windowed(2)
            .count { (a,b) -> a < b}

        println(changedCount2)
    }

    fun partTwo() {
        numbers
            .subList(0, numbers.lastIndex - 1)
            .mapIndexed { index, i ->
                i + numbers[index + 1] + numbers[index + 2]
            }
            .windowed(2)
            .count { (a, b) -> a < b }

        numbers
            .windowed(3)
            .map(List<Int>::sum)
            .windowed(2)
            .count { (a, b) -> a < b }
            .also(::println)
    }
}