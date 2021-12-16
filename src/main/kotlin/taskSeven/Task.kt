package taskSeven

import util.readPackageLocale
import kotlin.math.abs

object Task {

    private val input = readPackageLocale<Task>("input.txt")

    //-----------------------------------------
    //Input
    //-----------------------------------------
    private val positions = input[0]
            .split(",")
            .map(String::toInt)
    //-----------------------------------------

    private inline fun moveCrab(calculateExpense: (Int) -> Int) : Int {
        val min = positions.minOrNull() ?: 0
        val max = positions.maxOrNull() ?: 0

        return (min..max).minOf { p ->
            positions.sumOf { calculateExpense(abs(it - p)) }
        }
    }

    fun partOne() {
        //draft solution
//        val min = positions.minOrNull() ?: 0
//        val max = positions.maxOrNull() ?: 0
//
//        val minFuel : Int =
//            (min..max).minOf { p ->
//                positions.sumOf { abs(it - p) }
//            }
//
//        println(minFuel)

        //positions moved == fuel burned
        println(moveCrab { it })
    }

    fun partTwo() {
        //triangle number
        val linearExpense: (Int) -> Int = { it * (it + 1) / 2 }

        println(moveCrab(linearExpense))
    }
}