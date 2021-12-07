package me.nicolas.adventofcode.year2021

import me.nicolas.adventofcode.prettyPrint
import me.nicolas.adventofcode.readFileDirectlyAsText
import kotlin.math.abs
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

// https://adventofcode.com/2021/day/7
@OptIn(ExperimentalTime::class)
fun main() {

    val training = readFileDirectlyAsText("/year2021/day07/training.txt")
    val data = readFileDirectlyAsText("/year2021/day07/data.txt")
    val positions = data.split(",").map { it.toInt() }

    prettyPrint(
        message = "Part one answer = ",
        measureTimedValue { Day07().solve(positions) { position -> position } })

    prettyPrint(
        message = "Part two answer = ",
        measureTimedValue { Day07().solve(positions) { position -> (1..position).sumOf { it } } })
}

private class Day07 {

    fun solve(positions: List<Int>, costFunction: (position: Int) -> Int): Int {

        val max = positions.maxOf { it }
        val moves = (0..max).associateWith { positions.sumOf { position -> costFunction(abs(position - it)) } }
        return moves.values.minOf { it }
    }
}


