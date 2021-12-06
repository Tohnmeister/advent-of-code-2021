package nl.tonni.adventofcode2021

import kotlin.math.min
import kotlin.math.max

private data class Point(val x: Int, val y: Int) {

}
private data class Line(val start: Point, val end: Point) {
    val minX = min(start.x, end.x)
    val maxX = max(start.x, end.x)
    val minY = min(start.y, end.y)
    val maxY = max(start.y, end.y)
    val isStraight = start.x == end.x || start.y == end.y
    val allPoints = if (isStraight) (minX..maxX).flatMap { x -> (minY..maxY).map { y -> Point(x, y) }} else
        getIntProgression(start.x, end.x).zip(getIntProgression(start.y, end.y)) { x, y -> Point(x, y) }

    private fun getIntProgression(start: Int, end: Int): IntProgression {
        return if (start < end) {
            start..end
        } else {
            start downTo end
        }
    }
}

private fun createLine(lineStr: String): Line {
    val (startStr, endStr) = lineStr.split(" -> ")

    val start = createPoint(startStr)
    val end = createPoint(endStr)

    return Line(start, end)
}

private fun createPoint(pointStr: String): Point {
    val (x, y) = pointStr.split(',').map { it.toInt() }

    return Point(x, y)
}

fun main() {
    val lines = readLines("input/day5/input.txt").map(::createLine)

    val coveredPointCount = lines.fold(emptyMap<Point, Int>()) { accCoveredPointCount, line ->
        val newPointCount = line.allPoints.associateWith { point -> accCoveredPointCount.getOrDefault(point, 0) + 1 }
        accCoveredPointCount + newPointCount
    }

    val count = coveredPointCount.count { it.value > 1 }
    println(count)
}