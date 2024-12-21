package day01

import day01.utils.PointX
import java.io.File
import kotlin.math.abs

class Day21 : Base<List<String>, Long>(21) {

    enum class NumKey(val char: Char, val point: PointX) {
        N7('7', PointX(0, 0)),
        N8('8', PointX(0, 1)),
        N9('9', PointX(0, 2)),
        N4('4', PointX(1, 0)),
        N5('5', PointX(1, 1)),
        N6('6', PointX(1, 2)),
        N1('1', PointX(2, 0)),
        N2('2', PointX(2, 1)),
        N3('3', PointX(2, 2)),
        N0('0', PointX(3, 1)),
        A('A', PointX(3, 2))
    }

    enum class DirKey(val char: Char, val point: PointX) {
        UP('^', PointX(0, 1)),
        A('A', PointX(0, 2)),
        LEFT('<', PointX(1, 0)),
        DOWN('v', PointX(1, 1)),
        RIGHT('>', PointX(1, 2))
    }

    val dd = listOf(
        "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A",
        "<v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A",
        "<v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A",
        "<v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A",
        "<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A"
    )
    var i = 0

    override fun part1(input: List<String>): Long = input.fold(0L) { r, pass ->
        val result = calculate(pass)
        println(dd[i++].toList())
        println("${pass.dropLast(1).toLong()} * ${result.size}")
        r + (pass.dropLast(1).toLong() * result.size)
    }

    private fun calculate(input: String): List<Char> {
        val numResult = calculateNum(input)
        println(numResult)

        val dirResult1 = calculateDir(numResult)
        println(dirResult1)

        val dirResult2 = calculateDir(dirResult1)
        println(dirResult2)

        return dirResult2
    }

    private fun calculateNum(input: String): List<Char> {
        val result = mutableListOf<Char>()
        var point = NumKey.A.point
        input.map { NumKey.entries.find { numKey -> numKey.char == it }!!.point }.forEach { newPoint ->
            if (point.y == 0 && newPoint.x == 3) {
                val dx = point.x - newPoint.x
                if (dx > 0) repeat(dx) { result.add(DirKey.UP.char) } else repeat(abs(dx)) { result.add(DirKey.DOWN.char) }
                val dy = point.y - newPoint.y
                if (dy > 0) repeat(dy) { result.add(DirKey.LEFT.char) } else repeat(abs(dy)) { result.add(DirKey.RIGHT.char) }
            } else {
                val dy = point.y - newPoint.y
                if (dy > 0) repeat(dy) { result.add(DirKey.LEFT.char) } else repeat(abs(dy)) { result.add(DirKey.RIGHT.char) }
                val dx = point.x - newPoint.x
                if (dx > 0) repeat(dx) { result.add(DirKey.UP.char) } else repeat(abs(dx)) { result.add(DirKey.DOWN.char) }
            }
            result.add(DirKey.A.char)
            point = newPoint
        }
        return result
    }

    private fun calculateDir(input: List<Char>): List<Char> {
        val result = mutableListOf<Char>()
        var point = DirKey.A.point
        input.map { DirKey.entries.find { dirKey -> dirKey.char == it }!!.point }.forEach { newPoint ->
            if (point == DirKey.LEFT.point && newPoint.x == 0) {
                val dx = point.x - newPoint.x
                if (dx > 0) repeat(dx) { result.add(DirKey.UP.char) } else repeat(abs(dx)) { result.add(DirKey.DOWN.char) }
                val dy = point.y - newPoint.y
                if (dy > 0) repeat(dy) { result.add(DirKey.LEFT.char) } else repeat(abs(dy)) { result.add(DirKey.RIGHT.char) }
            } else {
                val dy = point.y - newPoint.y
                if (dy > 0) repeat(dy) { result.add(DirKey.LEFT.char) } else repeat(abs(dy)) { result.add(DirKey.RIGHT.char) }
                val dx = point.x - newPoint.x
                if (dx > 0) repeat(dx) { result.add(DirKey.UP.char) } else repeat(abs(dx)) { result.add(DirKey.DOWN.char) }
            }
            result.add(DirKey.A.char)
            point = newPoint
        }
        return result
    }

    override fun part2(input: List<String>): Long = 0L

    override fun mapInputData(file: File): List<String> = file.readLines()
}

fun main() {
    Day21().submitPart1TestInput() // failed
//    Day21().submitPart1Input() //
//    Day21().submitPart2TestInput() //
//    Day21().submitPart2Input() //
}