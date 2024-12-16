package day01.utils

import kotlin.math.abs

@JvmInline
value class PointX(private val value: Int) {
    constructor(x: Int, y: Int) : this(x * 1000 + y)
    val x: Int
        get() = value / 1000
    val y: Int
        get() = value % 1000
}

fun PointX.move(direction: Direction): Point<Int> =
    when (direction) {
        Direction.UP -> Point(this.x, this.y - 1)
        Direction.DOWN -> Point(this.x, this.y + 1)
        Direction.RIGHT -> Point(this.x + 1, this.y)
        Direction.LEFT -> Point(this.x - 1, this.y)
    }

fun PointX.move1(direction: Direction): PointX =
    when (direction) {
        Direction.UP -> PointX(this.x - 1, this.y)
        Direction.DOWN -> PointX(this.x + 1, this.y)
        Direction.RIGHT -> PointX(this.x, this.y + 1)
        Direction.LEFT -> PointX(this.x, this.y - 1)
    }

fun PointX.distance(anotherPoint: PointX): Int =
    (abs(this.x - anotherPoint.y) * abs(this.y - anotherPoint.y))
