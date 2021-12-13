package taskFive

import util.readPackageLocale
import kotlin.math.abs

object Task {
    //-----------------------------------------
    //Input
    //-----------------------------------------
    private val input = readPackageLocale<Task>("input.txt")
    private val lines = input
        .map {
            val nums = it
                .split(",", " -> ")
                .map(String::toInt)

            Line(nums[0], nums[1], nums[2], nums[3])
        }
    //-----------------------------------------


    fun partOne() {
        val map = Map()
        for (p in lines) {
            if (p.isAxesParallel()) {
                map.drawLine(p)
            }
        }
        println(map.determineIntersections(2))
    }

    fun partTwo() {
        val map = Map()
        for (p in lines) {
            if (p.isAxesParallel() || p.isDiagonal()) {
                map.drawLine(p)
            }
        }
        println(map.determineIntersections(2))
    }


    //-----------------------------------------
    //Helper classes
    //-----------------------------------------
    class Map {
        private val floor: MutableList<MutableList<Int>> = MutableList(1) { MutableList(1) { 0 } }

        fun determineIntersections(times: Int): Int {
            return floor.flatten().count { it >= times }
        }

        fun drawLine(l: Line) {
            when {
                l.isHorizontal() -> {
                    val x = l.p1.x
                    for (pY in l.yRange) {
                        drawPoint(x, pY)
                    }
                }
                l.isVertical() -> {
                    val y = l.p1.y
                    for (pX in l.xRange) {
                        drawPoint(pX, y)
                    }
                }
                l.isDiagonal() -> {
                    val xRangeIter = l.xRange.iterator()
                    val yRangeIter = l.yRange.iterator()

                    //since sides are equal ranges are also equal
                    while(xRangeIter.hasNext()) {
                        drawPoint(xRangeIter.next(), yRangeIter.next())
                    }
                }
            }
        }

        private fun drawPoint(x: Int, y: Int) {

            //numbering starts from 0
            val ySize = y + 1
            val xSize = x + 1

            //expanding the map downside
            val yDiff = floor.size - ySize
            if (yDiff < 0) {
                floor.addAll(List(-yDiff) { MutableList(floor[0].size) { 0 } })
            }

            //expanding the map to the right
            val xDiff = floor[y].size - xSize
            if (xDiff < 0) {
                floor.forEach {
                    it.addAll(List(-xDiff) { 0 })
                }
            }

            floor[y][x]++
        }

        override fun toString(): String {
            return floor.joinToString("\n")
        }
    }

    data class Line(
        val p1: Point,
        val p2: Point
    ) {
        constructor(x1: Int, y1: Int, x2: Int, y2: Int) :
                this(Point(x1, y1), Point(x2, y2))

        val xRange: IntProgression = if (p1.x < p2.x) {
            p1.x..p2.x
        } else {
            p1.x downTo p2.x
        }

        val yRange: IntProgression = if (p1.y < p2.y) {
            p1.y..p2.y
        } else {
            p1.y downTo p2.y
        }

        fun isAxesParallel(): Boolean {
            return isHorizontal() || isVertical()
        }

        fun isHorizontal(): Boolean {
            return p1.x == p2.x
        }

        fun isVertical(): Boolean {
            return p1.y == p2.y
        }

        fun isDiagonal(): Boolean {
            //side of the triangle are equal
            val a = abs(p2.x - p1.x)
            val b = abs(p2.y - p1.y)
            return a == b
        }
    }

    data class Point(
        val x: Int,
        val y: Int
    )
    //-----------------------------------------
}