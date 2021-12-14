package me.nicolas.adventofcode.year2021

import me.nicolas.adventofcode.prettyPrint
import me.nicolas.adventofcode.readFileDirectlyAsText
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue


// https://adventofcode.com/2021/day/14
@OptIn(ExperimentalTime::class)
fun main() {

    val training = readFileDirectlyAsText("/year2021/day14/training.txt")
    val data = readFileDirectlyAsText("/year2021/day14/data.txt")

    val (template, list) = training.split("\n\n")
    val insertions = list.split("\n").flatMap { it.split(" -> ") }.zipWithNext().toMap()

    prettyPrint(
        message = "Part one answer",
        measureTimedValue { Day14().partOne(template, insertions) })

    prettyPrint(
        message = "Part one bis answer",
        measureTimedValue { Day14().partOneBis(template, insertions) })

    prettyPrint(
        message = "Part one answer",
        measureTimedValue { Day14().partTwo(template, insertions) })
}

private class Day14 {

    fun partOne(template: String, insertions: Map<String, String>): Int {
        var result = template.toCharArray()
        repeat(10) {
            result = processStep(result, insertions)
        }
        val counts = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".map { char ->
            char to result.count { char == it }
        }.toMap()

        val filtered = counts.filter { it.value > 0 }

        return filtered.values.maxOf { it } - filtered.values.minOf { it }
    }

    fun partOneBis(template: String, insertionsRules: Map<String, String>): Long {
        return processSteps(10, template, insertionsRules)
    }

    fun partTwo(template: String, insertionsRules: Map<String, String>): Long {
        return processSteps(40, template, insertionsRules)
    }

    private fun processSteps(
        steps: Int,
        template: String,
        insertionsRules: Map<String, String>
    ): Long {
        var pairCount: Map<String, Long> = template
            .windowed(2)
            .groupingBy { it }.eachCount()
            .mapValues { it.value.toLong() }

        repeat(steps) {
            pairCount = pairCount
                .flatMap { (pair, count) ->
                    val stringToInsert = insertionsRules[pair]!!
                    listOf("${pair.first()}$stringToInsert" to count, "$stringToInsert${pair.last()}" to count)
                }.groupingBy { pair ->
                    pair.first
                }.fold(0L) { total, pair -> total + pair.second }
        }

        // count chars
        val charCount = pairCount
            .flatMap { (pair, count) -> listOf(pair.first() to count, pair.last() to count) }
            .groupingBy { pair -> pair.first }
            .fold(0L) { total, pair -> total + pair.second }
            .toMutableMap()

        // Add +1 to first and last
        charCount[template.first()] = charCount[template.first()]!! + 1
        charCount[template.last()] = charCount[template.last()]!! + 1

        return charCount.values.maxOf { it } / 2 - charCount.values.minOf { it } / 2
    }

    private fun processStep(template: CharArray, insertions: Map<String, String>): CharArray {
        val result = CharArray(template.size * 2 - 1)

        for (index in 0 until template.size - 1) {
            val pair = "${template[index]}${template[index + 1]}"

            result[2 * index] = template[index]
            result[2 * index + 1] = insertions[pair]!!.first()
        }
        result[result.size - 1] = template.last()

        return result
    }
}