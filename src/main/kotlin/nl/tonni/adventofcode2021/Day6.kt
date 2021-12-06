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

fun main() {
    val lanternFish = readLine("input/day6/input.txt").split(',').map { it.toInt() }

    println(produceLanternFish(lanternFish, 256).size)
}