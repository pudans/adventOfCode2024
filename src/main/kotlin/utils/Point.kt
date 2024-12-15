package day01.utils

import kotlin.math.abs

data class Point<T>(
    val x: T,
    val y: T
)

fun Point<Int>.move(direction: Direction): Point<Int> =
    when (direction) {
        Direction.UP -> Point(this.x, this.y - 1)
        Direction.DOWN -> Point(this.x, this.y + 1)
        Direction.RIGHT -> Point(this.x + 1, this.y)
        Direction.LEFT -> Point(this.x - 1, this.y)
    }

fun Point<Int>.distance(anotherPoint: Point<Int>): Int =
    (abs(this.x - anotherPoint.y) * abs(this.y - anotherPoint.y))
