package day01

import day01.utils.*
import java.io.File

class Day06 : Base<Day06.Data, Int>(6) {

    data class Data(
        val start: Point<Int>,
        val barriers: Set<Point<Int>>,
        val maxPoint: Point<Int>
    )

    override fun part1(input: Data): Int {
        val path = mutableSetOf<Point<Int>>()
        var direction = Direction.UP
        var point = input.start

        while (true) {
            path.add(point)
            val point1 = point.move1(direction)
            if (point1.x !in 0..input.maxPoint.x || point1.y !in 0..input.maxPoint.y) {
                break
            }
            if (input.barriers.contains(point1)) {
                direction = direction.turnRight()
            } else {
                point = point1
            }
        }

//        (0..input.maxPoint.x).forEach { x ->
//            println()
//            (0..input.maxPoint.y).forEach { y ->
//                val d = when {
//                    input.barriers.contains(Point(x, y)) -> '#'
//                    path.contains(Point(x, y)) -> 'X'
//                    else -> '.'
//                }
//                print(d)
//            }
//        }

        return path.size
    }

    override fun part2(input: Data): Int {
        data class Visitor(val point: Point<Int>, val direction: Direction)
        val path = mutableSetOf<Visitor>()
        val result = mutableSetOf<Point<Int>>()
        var direction = Direction.UP
        var point = input.start

        while (true) {
            path.add(Visitor(point, direction))
            val point1 = point.move1(direction)
            if (point1.x !in 0..input.maxPoint.x || point1.y !in 0..input.maxPoint.y) {
                break
            }
            if (input.barriers.contains(point1)) {
                direction = direction.turnRight()
            } else {
                point = point1
            }
        }

        path.forEach { visitor ->
            val blockedVisitor = mutableSetOf<Visitor>()
            var blockedDirection = visitor.direction.turnRight()
            var blockedPoint = visitor.point
            while (true) {
                blockedVisitor.add(Visitor(blockedPoint, blockedDirection))
                val point1 = blockedPoint.move1(blockedDirection)
                if (point1.x !in 0..input.maxPoint.x || point1.y !in 0..input.maxPoint.y) {
                    break
                }
                if (input.barriers.contains(point1)) {
                    blockedDirection = blockedDirection.turnRight()
                } else {
                    val visitor1 = Visitor(point1, blockedDirection)
                    if (blockedVisitor.contains(visitor1)) {
                        result.add(visitor.point.move1(visitor.direction))
                        break
                    }
                    blockedPoint = point1
                }
            }
            println()
            (0..input.maxPoint.x).forEach { x ->
                println()
                (0..input.maxPoint.y).forEach { y ->
                    val d = when {
                        result.contains(Point(x, y)) -> 'O'
                        input.barriers.contains(Point(x, y)) -> '#'
                        blockedVisitor.find { it.point == Point(x, y) } != null -> 'X'
                        else -> '.'
                    }
                    print(d)
                }
            }
        }

//        (0..input.maxPoint.x).forEach { x ->
//            println()
//            (0..input.maxPoint.y).forEach { y ->
//                val d = when {
//                    result.contains(Point(x, y)) -> 'O'
//                    input.barriers.contains(Point(x, y)) -> '#'
//                    path.find { it.point == Point(x, y) } != null -> 'X'
//                    else -> '.'
//                }
//                print(d)
//            }
//        }

        return result.size
    }


    override fun mapInputData(file: File): Data {
        var start: Point<Int>? = null
        val barriers = mutableSetOf<Point<Int>>()
        val lines = file.readLines()
        lines.forEachIndexed { x, line ->
            line.forEachIndexed { y, char ->
                when (char) {
                    '^' -> start = Point(x, y)
                    '#' -> barriers.add(Point(x, y))
                }
            }
        }
        return Data(
            start = start!!,
            barriers = barriers,
            maxPoint = Point(lines.lastIndex, lines.first().lastIndex)
        )
    }
}

fun main() {
//    Day06().submitPart1TestInput() // 41
//    Day06().submitPart1Input() // 5101
    Day06().submitPart2TestInput() // 6
//    Day06().submitPart2Input() // 913 FAILED!!
}