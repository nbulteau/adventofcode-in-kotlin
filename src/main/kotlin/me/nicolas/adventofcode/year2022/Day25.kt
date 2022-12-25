package me.nicolas.adventofcode.year2022

import me.nicolas.adventofcode.AdventOfCodeDay
import me.nicolas.adventofcode.prettyPrintPartOne
import me.nicolas.adventofcode.readFileDirectlyAsText

fun main() {
    val training = readFileDirectlyAsText("/year2022/day25/training.txt")
    val data = readFileDirectlyAsText("/year2022/day25/data.txt")

    val inputs = data.split("\n")

    val day = Day25("--- Day 25: Full of Hot Air ---", "https://adventofcode.com/2022/day/25")
    prettyPrintPartOne { day.partOne(inputs) }
}

private class Day25(title: String, adventOfCodeLink: String) : AdventOfCodeDay(title, adventOfCodeLink) {

    private val snafuValues = arrayOf(0, 1, 2, -2, -1)
    private val snafuDigits = "012=-".toCharArray()

    private fun Char.snafuToDigit(): Int = when (this) {
        '=' -> -2
        '-' -> -1
        else -> this.digitToInt()
    }

    private fun String.snafuToDecimal() = this.indices.fold(0L) { acc, i -> acc * 5 + this[i].snafuToDigit() }

    fun Long.decimalToSNAFU(): String {
        var decimal = this
        var result = ""
        while (decimal > 0) {
            val index = (decimal % 5).toInt()
            result += snafuDigits[index]
            decimal = (decimal - snafuValues[index]) / 5
        }
        return result.reversed()
    }

    fun partOne(inputs: List<String>): String {
        val numbers = inputs.sumOf { number ->
            number.snafuToDecimal()
        }
        return numbers.decimalToSNAFU()
    }
}
 
