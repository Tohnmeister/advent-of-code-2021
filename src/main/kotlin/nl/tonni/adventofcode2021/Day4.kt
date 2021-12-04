package nl.tonni.adventofcode2021

class BingoCard(private val numbersAndState: List<List<Pair<Int, Boolean>>>) {
    private val nrOfColumns = numbersAndState[0].size

    /**
     * Places a number and returns a new bingo card.
     */
    fun placeNumber(placedNumber: Int): BingoCard {
        val newNumbersAndState = numbersAndState
            .map { row ->
                row.map { (number, checked) ->
                    if (number == placedNumber) Pair(number, true) else Pair(number, checked)
                }
            }

        return BingoCard(newNumbersAndState)
    }

    /**
     * Returns true if the current bingo card has a bingo in a row or column.
     */
    fun hasBingo(): Boolean {
        val hasHorizontalMatch = numbersAndState.any { row -> row.all { it.second }}

        if (hasHorizontalMatch) {
            // No need to check vertical match.
            return true
        }

        val hasVerticalMatch = (0 until nrOfColumns).any { columnIndex ->
            numbersAndState.all { it[columnIndex].second }
        }

        return hasVerticalMatch
    }

    override fun toString(): String {
        val nrOfRows = numbersAndState.size

        return buildString {
            appendLine("-----")
            for (row in numbersAndState) {
                for (number in row) {
                    append(number)
                }
                appendLine()
            }
            for (i in 0 until nrOfColumns + 2) {
                append('-')
            }
            appendLine("-----")
        }
    }
}

private fun creatEmptyBingoCard(numberRows: List<List<Int>>): BingoCard {
    return BingoCard(numberRows.map { row -> row.map { Pair(it, false) } })
}

fun main() {
    val lines = readLines("input/day4/input.txt")
    val moves = lines[0].split(",").map { it.toInt() }
    val bingoCards = lines
        .drop(2)
        .windowed(5, 6)
        .map { cardLines ->
            cardLines.map { cardLine ->
                cardLine.split(" ").filter { nr -> nr.isNotBlank() }.map { nr -> nr.toInt() }
            }
        }
        .map(::creatEmptyBingoCard)

    val bingoCard = bingoCards[0]
    println(bingoCard)

    val bingo = bingoCard.placeNumber(31)
        .placeNumber(33)
        .placeNumber(77)
        .placeNumber(21)
        .placeNumber(95)
        .hasBingo()

    println(bingo)

}