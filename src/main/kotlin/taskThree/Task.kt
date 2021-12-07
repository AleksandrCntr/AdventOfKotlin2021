package taskThree

import util.readPackageLocale

object Task {
    private val inputs = readPackageLocale<Task>("input.txt")
    private val ints = inputs.map { it.toInt(2) }
    private val length = inputs[0].length

    fun partOne() {
        val onesCount = List(length) { 0 }
            .toMutableList()

        for (input in ints) {
            for (i in 0 until length) {
                if (input.bitAt(i))
                    onesCount[i]++
                else
                    onesCount[i]--
            }
        }

        val binary = onesCount.reversed().fold(0) { acc, i ->
            val bit = if (i > 0) 1 else 0
            (acc shl 1) or bit
        }

        val binaryRightInverted = binary.inv() and (-1 shl length).inv()

        println(binary * binaryRightInverted)
    }

    fun partTwo() {
        var oxygenRating = 0
        var co2Rating = 0

        var filteredNumbers = ints
        for (i in (length - 1) downTo 0) {
            val mostCommonBit = filteredNumbers.onesOfZeros(i)

            filteredNumbers = if (mostCommonBit >= 0)
                filteredNumbers.filter { it.bitAt(i) }
            else
                filteredNumbers.filter { !it.bitAt(i) }

            if (filteredNumbers.size == 1) break
        }

        oxygenRating = filteredNumbers[0]

        filteredNumbers = ints
        for (i in (length - 1) downTo 0) {
            val mostCommonBit = filteredNumbers.onesOfZeros(i)

            filteredNumbers = if (mostCommonBit < 0)
                filteredNumbers.filter { it.bitAt(i) }
            else
                filteredNumbers.filter { !it.bitAt(i) }

            if (filteredNumbers.size == 1) break
        }

        co2Rating = filteredNumbers[0]

        println(oxygenRating * co2Rating)
    }

    private fun List<Int>.onesOfZeros(position: Int): Int {
        var onesCount = 0

        this.forEach {
            if (it.bitAt(position)) onesCount++
            else onesCount--
        }

        return onesCount
    }

    private fun Int.bitAt(position: Int): Boolean {
        return ((this shr position) and 1) == 1
    }
}