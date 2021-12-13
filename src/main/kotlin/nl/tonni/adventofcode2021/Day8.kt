package nl.tonni.adventofcode2021

data class SignalPattern(val segments: List<Char>) {
    fun getDigit(): Int? = when (segments.size) {
        2 -> 1
        3 -> 7
        4 -> 4
        7 -> 8
        else -> null
    }

}

data class Entry(val signalPatterns: List<SignalPattern>, val outputValue: List<SignalPattern>)

fun main() {
    val lines = readLines("input/day8/input.txt")
    val entries = lines.map { it.split(" | ") }.map { line ->
        val signalPatternsStr = line[0]
        val outputValueStr = line[1]
        val signalPatterns = signalPatternsStr.split(" ").map(String::toList).map { SignalPattern(it) }
        val outputValue = outputValueStr.split(" ").map(String::toList).map {SignalPattern(it) }

        Entry(signalPatterns, outputValue)
    }

    println(entries.flatMap { it.outputValue }.count { it.getDigit() != null })
}