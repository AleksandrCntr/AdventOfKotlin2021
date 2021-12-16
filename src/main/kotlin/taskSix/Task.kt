package taskSix

import util.readPackageLocale

object Task {

    private val input = readPackageLocale<Task>("input.txt")

    //-----------------------------------------
    //Input
    //-----------------------------------------
    private val schoolInput
        get() = input[0]
            .split(",")
            .map(String::toByte)
            .map(::LatternFish)

    //-----------------------------------------


    fun partOne() {
        val fishSchool = School(schoolInput)
        fishSchool.progress(80)

        println(fishSchool.count())
    }

    fun partTwo() {

//        val bigSchool = BigSchool(School(schoolInput), 256, 128)
//        val bigSchool = BigSchool(School(schoolInput), 256, 64)
        val bigSchool = BigSchool(School(schoolInput), 256, 4)

        println(bigSchool.recursiveCount())
    }


    //-----------------------------------------
    //Helper classes PART TWO
    //-----------------------------------------
    class BigSchool(private val initialSchool: School, progressDays: Int, private val progression: Int) {

        //Since fish product same offspring group over days there is no need to calculate them again
        // (Value, Days) -> sum
        private val shortcuts: MutableMap<Pair<Byte, Int>, Long> = mutableMapOf()

        //should be dividable without a remainder
        private val levels = progressDays / progression

        init {
            //perform calculations for base level, bottom of the tree
            repeat(9) { value ->

                val school = School(LatternFish(value.toByte()))
                school.progress(progression)
                shortcuts[value.toByte() to progression] = school.count().toLong()
            }
        }

        //Walking down the tree, progressing by days on each value
        //Until reaching the very bottom (level == 1), where base shortcuts, which were init above, are found
        //than perform a sum of the branches, and saving a new shortcut for calculated branch
        fun recursiveCount(fish: School = initialSchool, days: Int = progression, level: Int = levels) : Long {
            return fish.fish.sumOf { f ->
                shortcuts[f.timer to days * level] ?:
                    recursiveCount(
                        School(LatternFish(f.timer))
                            .apply {
                                progress(days)
                            },
                        days = days,
                        level = level - 1
                    )
                        .also {
                            //saving the long value result after calculation
                            shortcuts[f.timer to days * level] = it
                        }
            }
        }
    }

    //-----------------------------------------
    //Helper classes PART ONE
    //-----------------------------------------
    class School(fish: List<LatternFish>) {

        private var mutableFish: MutableList<LatternFish> = fish.toMutableList()
        val fish: List<LatternFish> = mutableFish

        private var bornCount = 0

        constructor(fish: LatternFish) : this(listOf(fish))

        fun progress(days: Int) = repeat(days) {

            bornCount = mutableFish.count(LatternFish::timerAtZero)

            mutableFish.forEach(LatternFish::day)
            mutableFish.addAll(List(bornCount) { LatternFish() })
        }

        fun count() : Int {
            return mutableFish.count()
        }

        override fun toString(): String {
            return mutableFish.joinToString(" ")
        }
    }

    //Byte limit is enough to store -1..8 range
    class LatternFish(timer: Byte = 8) {
        var timer: Byte = timer
            private set

        companion object {
            const val BYTE_MINUS_ONE: Byte = -1
            const val BYTE_ZERO: Byte = 0
        }

        fun day() {
            timer--
            if (timer == BYTE_MINUS_ONE) timer = 6
        }

        fun timerAtZero() : Boolean {
            return timer == BYTE_ZERO
        }

        override fun toString() = timer.toString()
    }
    //-----------------------------------------
}