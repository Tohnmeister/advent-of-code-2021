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

    /**
     * Checks whether this card has a bingo with this move, and not already with other moves.
     */
    fun hasBingoWithMove(move: Int): Boolean {
        val horizontalMatches = numbersAndState.filter { row -> row.all { it.second }}

        val columns = (0 until nrOfColumns).map { columnIndex -> numbersAndState.map { row -> row[columnIndex] }}

        val verticalMatches = columns.filter { column -> column.all { it.second }}

        val nrOfMatches = horizontalMatches.size + verticalMatches.size
        if (nrOfMatches == 0 || nrOfMatches > 1) {
            return false
        }
        if (horizontalMatches.isNotEmpty()) {
            return horizontalMatches.first().any { it.first == move }
        }
        return verticalMatches.first().any { it.first == move }
    }

    fun sumOfUnmarkedNumbers(): Int {
        return numbersAndState.flatten().filter { !it.second }.sumOf { it.first }
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

/**
 * Places a number in all of the boards and returns the list of new boards
 */
private fun List<BingoCard>.placeNumber(number: Int): List<BingoCard> {
    return map { it.placeNumber(number) }
}

private fun List<BingoCard>.firstThatHasBingo(): BingoCard? {
    return firstOrNull { it.hasBingo() }
}

private fun List<BingoCard>.firstThatHasBingoWithMove(move: Int): BingoCard? {
    return firstOrNull { it.hasBingoWithMove(move) }
}

tailrec fun findBingo(bingoCards: List<BingoCard>, remainingMoves: List<Int>): Pair<Int, BingoCard>? {
    if (remainingMoves.isEmpty()) {
        return null
    }

    val nextMove = remainingMoves.first()
    val newBingoCards = bingoCards.placeNumber(nextMove)
    val winningBingoCard = newBingoCards.firstThatHasBingo()

    if (winningBingoCard != null) {
        return Pair(nextMove, winningBingoCard)
    }

    return findBingo(newBingoCards, remainingMoves.drop(1))
}

fun findLastBingo(bingoCards: List<BingoCard>, remainingMoves: List<Int>, lastWinningMoveAndCard: Pair<Int, BingoCard>?): Pair<Int, BingoCard>? {
    if (remainingMoves.isEmpty()) {
        return lastWinningMoveAndCard
    }

    val nextMove = remainingMoves.first()
    val newBingoCards = bingoCards.placeNumber(nextMove)
    val winningBingoCard = newBingoCards.firstThatHasBingoWithMove(nextMove)

    val newLastWinningMoveAndCard = if (winningBingoCard != null) Pair(nextMove, winningBingoCard) else lastWinningMoveAndCard

    return findLastBingo(newBingoCards, remainingMoves.drop(1), newLastWinningMoveAndCard)
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

    val (winningMove, winningBingoCard) = findBingo(bingoCards, moves)!!

    println(winningMove * winningBingoCard.sumOfUnmarkedNumbers())

    val (lastWinningMove, lastWinningBingoCard) = findLastBingo(bingoCards, moves, null)!!

    println(lastWinningMove * lastWinningBingoCard.sumOfUnmarkedNumbers())
}