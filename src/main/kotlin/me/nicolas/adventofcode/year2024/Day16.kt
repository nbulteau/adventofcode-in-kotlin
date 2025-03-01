package me.nicolas.adventofcode.year2024

import me.nicolas.adventofcode.utils.*
import java.util.*

// --- Day 16: Reindeer Maze ---
// https://adventofcode.com/2024/day/16
fun main() {
    val data = readFileDirectlyAsText("/year2024/day16/data.txt")
    val day = Day16(2024, 16)
    prettyPrintPartOne { day.partOne(data) }
    prettyPrintPartTwo { day.partTwo(data) }
}

/**
 * To solve this problem, we need to find all the shortest paths from the start tile 'S' to the end tile 'E' in the maze.
 * We can use Dijkstra's algorithm to achieve this, as it is well-suited for finding the shortest path in a weighted graph.
 * In this case, the weights are the scores associated with moving forward (1 point) and turning (1000 points).
 *
 * Part One: Finding the Lowest Score
 * - Parse the Input: Read the maze from the input file and convert it into a grid.
 * - Initialize Dijkstra's Algorithm: Start from the 'S' tile facing East and use a priority queue to explore the paths.
 * - Explore Paths: For each path, calculate the score for moving forward, turning left, and turning right. Add these new paths to the priority queue.
 * - Track Scores: Keep track of the minimum score to reach the 'E' tile.
 *
 * Part Two: Finding All Best Paths
 * - Run Dijkstra's Algorithm: As in Part One, but this time, keep track of all the tiles that are part of the best paths.
 * - Mark Best Path Tiles: Once the shortest path(s) are found, mark all the tiles that are part of these paths.
 */
class Day16(year: Int, day: Int, title: String = "Reindeer Maze") : AdventOfCodeDay(year, day, title) {


    fun partOne(data: String): Int {
        val maze = SimpleGrid.of(data)
        val (score, _) = maze.dijkstra()

        return score
    }

    fun partTwo(data: String): Int {
        val maze = SimpleGrid.of(data)
        val (_, seats) = maze.dijkstra()

        return seats.size
    }

    // Dijkstra's algorithm to find all the shortest paths from S to E
    // https://www.redblobgames.com/pathfinding/a-star/implementation.html#python-dijkstra
    private fun SimpleGrid<Char>.dijkstra(): Pair<Int, Set<Point>> {
        val queue = PriorityQueue<Path>(compareBy { path -> path.score })
        val start = findOne { it == 'S' }
        val end = findOne { it == 'E' }

        // Start Tile (marked S) facing East
        queue.add(Path(score = 0, points = listOf(start), direction = Directions.right))

        var score = Int.MAX_VALUE
        val scores = mutableMapOf<Pair<Point, Pair<Int, Int>>, Int>()
        val seats = mutableSetOf<Point>()

        while (queue.isNotEmpty()) {
            val path = queue.poll()
            // The key is the current point and the direction
            val key = path.key

            if (path.end == end) {
                if (path.score <= score) {
                    score = path.score
                } else {
                    break
                }
                seats.addAll(path.points)
            }

            // Don't revisit points with a worse score
            if (scores.containsKey(key) && scores[key]!! < path.score) {
                continue
            }
            scores[key] = path.score

            val nextPoint = path.nextPoint

            // As long as there isn't a wall, we can proceed forwards
            if (this[nextPoint] != '#') {
                queue.add(path.move())
            }
            queue.add(path.turnLeft())
            queue.add(path.turnRight())
        }

        return score to seats
    }

    private object Directions {

        val up = -1 to 0
        val right = 0 to 1
        val down = 1 to 0
        val left = 0 to -1

        val cardinals = listOf(up, right, down, left)
    }

    private data class Path(
        val score: Int,
        val points: List<Point>,
        val direction: Pair<Int, Int>,
    ) {
        fun Pair<Int, Int>.turnLeft(): Pair<Int, Int> =
            Directions.cardinals[(Directions.cardinals.indexOf(this) - 1).mod(4)]

        fun Pair<Int, Int>.turnRight(): Pair<Int, Int> =
            Directions.cardinals[(Directions.cardinals.indexOf(this) + 1).mod(4)]

        val end
            get() = points.last()

        val key
            get() = end to direction

        val nextPoint
            get() = end + direction

        fun turnLeft() = copy(score = score + 1000, direction = direction.turnLeft())
        fun turnRight() = copy(score = score + 1000, direction = direction.turnRight())

        fun move() = copy(score = score + 1, points = points + (end + direction))
    }
}


