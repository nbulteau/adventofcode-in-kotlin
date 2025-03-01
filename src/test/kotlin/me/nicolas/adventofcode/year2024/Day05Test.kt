package me.nicolas.adventofcode.year2024

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day05Test {
    private val day = Day05(2024, 5, "Print Queue")

    val testPart1 = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13
    """.trimIndent()

    val testPart2 = """
        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent()

    @Test
    fun partOne() {
        assertEquals(143, day.partOne(testPart1 + "\n\n" + testPart2))
    }

    @Test
    fun partTwo() {
        assertEquals(123, day.partTwo(testPart1 + "\n\n" + testPart2))
    }
}