package taskFour

import util.readPackageLocale

object Task {

    private val input = readPackageLocale<Task>("input.txt")

    //-----------------------------------------
    //Input
    //-----------------------------------------
    private val numbersPool = input[0]
        .split(',')
        .map(String::toInt)

    private val decksInput = input
        .slice(2..input.lastIndex)
        .filter(String::isNotBlank)
        .windowed(5, 5)
        .map { row ->

            val cells = List(5) { i ->

                val rowNumbers = row[i]
                    .split(' ')
                    .filter(String::isNotBlank)
                    .map(String::toInt)

                List(5) { j ->
                    BingoDeck.Cell(rowNumbers[j])
                }
            }

            BingoDeck(cells)
        }

    private val decksInGame = decksInput.toList()
    //-----------------------------------------


    fun partOne() {
        var winningScore: Int = 0

        numbersPool.any { number ->
            decksInGame.forEach { it.markIfContains(number) }
            decksInGame.any { deck ->
                if (deck.isWinner()) {
                    winningScore = number * deck.allUnmarked().sum()
                    return@any true
                } else return@any false
            }
        }

        println(winningScore)
    }

    //let the giant squid win :)))
    fun partTwo() {
        var lastWinningScore: Int = 0
        var decksInGameFiltered : MutableList<BingoDeck> = decksInGame.toMutableList()

        numbersPool.forEach { number ->
            decksInGameFiltered.forEach { it.markIfContains(number) }
            decksInGameFiltered = decksInGameFiltered.filter { deck ->
                val isWinnerDeck = deck.isWinner()
                if (isWinnerDeck) {
                    lastWinningScore = number * deck.allUnmarked().sum()
                }
                !isWinnerDeck
            }.toMutableList()
        }

        println(lastWinningScore)
    }


    //-----------------------------------------
    //Helper classes
    //-----------------------------------------
    data class BingoDeck(
        private val cells: List<List<Cell>>
    ) {
        data class Cell(
            val number: Int,
            var isMarked: Boolean = false
        ) {
            fun mark(flag: Boolean = true) {
                isMarked = flag
            }
        }

        fun allUnmarked() : List<Int> {
            return cells.flatten()
                .filter { !it.isMarked }
                .map { it.number }
        }

        fun markIfContains(value: Int) {
            for (row in cells) {
                for (num in row) {
                    if (num.number == value) {
                        num.mark()
                    }
                }
            }
        }

        fun isWinner(): Boolean {
//            val horizontalWinner = cells.any { it.all(Cell::isMarked) }

            //hardcoded because yes, thx
            repeat(5) { a ->

                var winnerColumn = true
                var winnerRow = true

                repeat(5) { b ->

                    if (!cells[a][b].isMarked) {
                        winnerRow = false
                    }

                    if (!cells[b][a].isMarked) {
                        winnerColumn = false
                    }

                    if (!winnerColumn && !winnerRow) return@repeat
                }

                if (winnerColumn || winnerRow) {
                    return true
                }
            }

            return false
        }

        override fun toString(): String {
            return cells.joinToString("\n")
        }
    }
    //-----------------------------------------
}