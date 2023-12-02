package me.nicolas.adventofcode.year2023

import me.nicolas.adventofcode.AdventOfCodeDay
import me.nicolas.adventofcode.prettyPrintPartOne
import me.nicolas.adventofcode.prettyPrintPartTwo
import me.nicolas.adventofcode.readFileDirectlyAsText

fun main() {

    val training = readFileDirectlyAsText("/year2023/day02/training.txt")
    val data = readFileDirectlyAsText("/year2023/day02/data.txt")

    val lines = training.split("\n")

    val day = Day02("--- Day 2: Cube Conundrum ---", "https://adventofcode.com/2023/day/2")
    prettyPrintPartOne { day.partOne(lines) }
    prettyPrintPartTwo { day.partTwo(lines) }
}

class Day02(title: String, adventOfCodeLink: String) : AdventOfCodeDay(title, adventOfCodeLink) {
    fun partOne(records: List<String>): Int {
        val redMax = 12
        val greenMax = 13
        val blueMax = 14

        var result = 0
        records.forEachIndexed { index, record ->
            var setsAreOk = true
            val sets = extractSets(record)
            for (set in sets) {
                val (blue, red, green) = extractRGB(set)
                setsAreOk = (blue <= blueMax && red <= redMax && green <= greenMax)
                if (!setsAreOk) {
                    break
                }
            }
            if (setsAreOk) {
                result += index + 1
            }
        }

        return result
    }


    fun partTwo(records: List<String>): Int {
        return records.sumOf { record ->
            var redMax = 0
            var greenMax = 0
            var blueMax = 0

            val sets = extractSets(record)
            for (set in sets) {
                val (blue, red, green) = extractRGB(set)

                blueMax = maxOf(blue, blueMax)
                redMax = maxOf(red, redMax)
                greenMax = maxOf(green, greenMax)
            }
            (redMax * greenMax * blueMax)
        }
    }

    private fun extractSets(record: String) = record.substringAfter(": ").split("; ")

    private fun extractRGB(set: String): Triple<Int, Int, Int> {
        var blue = 0
        var red = 0
        var green = 0

        val scores = set.split(", ")
        for (score in scores) {
            val (value, cube) = score.split(" ")
            when (cube) {
                "blue" -> blue += value.toInt()
                "red" -> red += value.toInt()
                "green" -> green += value.toInt()
            }
        }
        return Triple(blue, red, green)
    }
}

