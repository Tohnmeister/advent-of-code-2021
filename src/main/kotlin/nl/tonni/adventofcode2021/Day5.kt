package nl.tonni.adventofcode2021

private data class Point(val x: Int, val y: Int)
private data class Line(val start: Point, val end: Point)

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
    val lines = readLines("input/day5/test.txt").map(::createLine)


}