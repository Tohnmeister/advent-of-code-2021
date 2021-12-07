package nl.tonni.adventofcode2021

tailrec fun produceLanternFish(lanternFish: List<Int>, nrOfRemainingDays: Int): List<Int> {
    if (nrOfRemainingDays == 0) {
        return lanternFish
    }

    val nrOfNewLanternFish = lanternFish.count { it == 0 }
    val newLanternFish = List(nrOfNewLanternFish) { 8 }

    val existingLanternFish = lanternFish.map { if (it == 0) 6 else it - 1 }

    return produceLanternFish(existingLanternFish + newLanternFish, nrOfRemainingDays - 1)
}

tailrec fun produceLanternFish(lanternFishHistogram: Map<Int, Long>, nrOfRemainingDays: Int): Map<Int, Long> {
    if (nrOfRemainingDays == 0) {
        return lanternFishHistogram
    }

    val newLanternFishHistogram = mapOf(
        8 to lanternFishHistogram.getOrDefault(0, 0),
        7 to lanternFishHistogram.getOrDefault(8, 0),
        6 to lanternFishHistogram.getOrDefault(7, 0) + lanternFishHistogram.getOrDefault(0, 0),
        5 to lanternFishHistogram.getOrDefault(6, 0),
        4 to lanternFishHistogram.getOrDefault(5, 0),
        3 to lanternFishHistogram.getOrDefault(4, 0),
        2 to lanternFishHistogram.getOrDefault(3, 0),
        1 to lanternFishHistogram.getOrDefault(2, 0),
        0 to lanternFishHistogram.getOrDefault(1, 0),
    )

    return produceLanternFish(newLanternFishHistogram, nrOfRemainingDays - 1)
}

fun main() {
    val lanternFish = readLine("input/day6/input.txt").split(',').map { it.toInt() }
    val lanternFishHistogram = lanternFish.groupingBy { it }.eachCount().mapValues { it.value.toLong() }
    println(produceLanternFish(lanternFishHistogram, 256).values.sum())
}