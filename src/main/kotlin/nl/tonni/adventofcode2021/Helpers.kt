package nl.tonni.adventofcode2021

import java.io.File

fun readLinesToInts(pathname: String) = readLines(pathname).map { it.toInt() }
fun readLines(pathname: String) = File(pathname).readLines()
fun readLine(pathname: String) = readLines(pathname)[0]