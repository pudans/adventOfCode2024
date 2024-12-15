package day01

import day01.utils.Point
import java.io.File
import kotlin.math.roundToLong

class Day13 : Base<List<Day13.Play>, Long>(13) {

    companion object {
        private const val MAX_PRESS = 100L
    }

    data class Play(
        val a: Point<Double>,
        val b: Point<Double>,
        val prize: Point<Double>
    )

    override fun part1(input: List<Play>): Long = input.fold(0L) { r, play -> r + resolve(play) }

//    Button A: X+94, Y+34
//    Button B: X+22, Y+67
//    Prize: X=8400, Y=5400

    private fun resolve(play: Play, maxA: Long = MAX_PRESS, maxB: Long = MAX_PRESS): Long {
        val b = ((play.prize.x * play.a.y - play.prize.y * play.a.x) / (play.b.x * play.a.y - play.a.x * play.b.y)).roundToLong()
        val a = ((play.prize.y / play.a.y) - (play.b.y * b) / play.a.y).roundToLong()
        val test1 = (play.a.x * a + play.b.x * b) == play.prize.x
        val test2 = (play.a.y * a + play.b.y * b) == play.prize.y

        val result = if (a in 0..maxA && b in 0..maxB && test1 && test2) a * 3 + b else 0L
        return result
    }

    override fun part2(input: List<Play>): Long {
        val add = 10000000000000
        val newInput = input.map {
            it.copy(prize = Point(it.prize.x + add, it.prize.y + add))
        }

        return newInput.fold(0L) { r, play -> r + resolve(play, maxA = Long.MAX_VALUE, maxB = Long.MAX_VALUE) }
    }

    override fun mapInputData(file: File): List<Play> =
        file.readLines().chunked(4).map { line ->
            val d1 = line[0].removePrefix("Button A: ").split(", ").map { it.split("+") }
            val d2 = line[1].removePrefix("Button B: ").split(", ").map { it.split("+") }
            val d3 = line[2].removePrefix("Prize: ").split(", ").map { it.split("=") }
            Play(
                a = Point(d1[0][1].toDouble(), d1[1][1].toDouble()),
                b = Point(d2[0][1].toDouble(), d2[1][1].toDouble()),
                prize = Point(d3[0][1].toDouble(), d3[1][1].toDouble())
            )
        }
}

fun main() {
    Day13().submitPart1TestInput() // 480
    Day13().submitPart1Input() // 36838
    Day13().submitPart2TestInput() // 875318608908 ?
    Day13().submitPart2Input() // 83029436920891
}