package nl.tonni.adventofcode2021

data class SignalPattern(val segments: Set<Char>) {
    val nrOfSegments = segments.size

    fun getDigit(): Int? = when (segments.size) {
        2 -> 1
        3 -> 7
        4 -> 4
        7 -> 8
        else -> null
    }

    operator fun minus(other: SignalPattern): SignalPattern {
        return SignalPattern(this.segments - other.segments)
    }

    operator fun plus(other: SignalPattern): SignalPattern {
        return SignalPattern(this.segments + other.segments)
    }

    fun isSuperSetBut(other: SignalPattern, nrOfExtraItems: Int) =
        this.segments.containsAll(other.segments) && (this - other).nrOfSegments == nrOfExtraItems
}

private fun List<SignalPattern>.firstThatIsSuperSetBut(other: SignalPattern, nrOfExtraItems: Int): SignalPattern? {
    return firstOrNull { it.isSuperSetBut(other, nrOfExtraItems) }
}

data class Entry(val signalPatterns: List<SignalPattern>, val outputValueSignalPatterns: List<SignalPattern>) {
    private val signalPattern1 = signalPatterns.first { it.getDigit() == 1 }
    private val signalPattern4 = signalPatterns.first { it.getDigit() == 4 }
    private val signalPattern7 = signalPatterns.first { it.getDigit() == 7 }
    private val signalPattern8 = signalPatterns.first { it.getDigit() == 8 }
    private val signalPatternA = signalPattern7 - signalPattern1
    private val signalPatternBd = signalPattern4 - signalPattern1
    private val signalPattern9 = signalPatterns.firstThatIsSuperSetBut(signalPatternA + signalPattern4, 1)!!
    private val signalPatternG = signalPattern9 - signalPatternA - signalPattern4
    private val signalPatternE = signalPattern8 - signalPattern9
    private val signalPattern6 = signalPatterns.firstThatIsSuperSetBut(signalPatternA + signalPatternBd + signalPatternE + signalPatternG, 1)!!
    private val signalPatternF = signalPattern6 - signalPatternA - signalPatternBd - signalPatternE - signalPatternG
    private val signalPatternC = signalPattern1 - signalPatternF
    private val signalPattern5 = signalPattern9 - signalPatternC
    private val signalPattern3 = signalPatterns.firstThatIsSuperSetBut(signalPatternA + signalPatternC + signalPatternF + signalPatternG, 1)!!
    private val signalPatternD = signalPattern3 - (signalPatternA + signalPatternC + signalPatternF + signalPatternG)
    private val signalPatternB = signalPatternBd - signalPatternD
    private val signalPattern0 = signalPattern8 - signalPatternD
    private val signalPattern2 = signalPattern8 - signalPatternB - signalPatternF

    fun getOutputValue(): Int =
        getOutputValue(outputValueSignalPatterns[0]) * 1000 + getOutputValue(outputValueSignalPatterns[1]) * 100 + getOutputValue(outputValueSignalPatterns[2]) * 10 + getOutputValue(outputValueSignalPatterns[3])

    private fun getOutputValue(signalPattern: SignalPattern) = when (signalPattern) {
        signalPattern0 -> 0
        signalPattern1 -> 1
        signalPattern2 -> 2
        signalPattern3 -> 3
        signalPattern4 -> 4
        signalPattern5 -> 5
        signalPattern6 -> 6
        signalPattern7 -> 7
        signalPattern8 -> 8
        signalPattern9 -> 9
        else -> throw RuntimeException("Unexpected signal pattern $signalPattern")
    }
}

fun main() {
    val lines = readLines("input/day8/input.txt")
    val entries = lines.map { it.split(" | ") }.map { line ->
        val signalPatternsStr = line[0]
        val outputValueStr = line[1]
        val signalPatterns = signalPatternsStr.split(" ").map(String::toSet).map { SignalPattern(it) }
        val outputValue = outputValueStr.split(" ").map(String::toSet).map {SignalPattern(it) }

        Entry(signalPatterns, outputValue)
    }

    println(entries.flatMap { it.outputValueSignalPatterns }.count { it.getDigit() != null })

    println(entries.sumOf { it.getOutputValue() })
}