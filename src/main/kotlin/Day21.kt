package day01

import day01.utils.PointX
import day01.utils.println
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
        NONE('.', PointX(3, 0)),
        N0('0', PointX(3, 1)),
        A('A', PointX(3, 2))
    }

    enum class DirKey(val char: Char, val point: PointX) {
        NONE('.', PointX(0, 0)),
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
        val points = pass.map { NumKey.entries.first { numKey -> numKey.char == it }.point }
        val result = calculation(NumKey.A.point, NumKey.NONE.point, points, 0, 3)!!

        println("${result.map { p -> DirKey.entries.first { it.point == p }.char }}")

        println(dd[i++].toList())
        println("${pass.dropLast(1).toLong()} * ${result.size}")
        r + (pass.dropLast(1).toLong() * result.size)
    }

    private fun calculation(start: PointX, nonePoint: PointX, input: List<PointX>, level: Int, maxLevel: Int): List<PointX>? {
        if (input.isEmpty()) return null
        if (level == maxLevel) return input

        val result = mutableListOf<PointX>()
        var point = start
        input.forEach { newPoint ->
            val dx = point.x - newPoint.x
            val dy = point.y - newPoint.y

            val variant1 = mutableListOf<PointX>()
            repeat(abs(dx)) { if (dx > 0) variant1.add(DirKey.UP.point) else variant1.add(DirKey.DOWN.point) }
            repeat(abs(dy)) { if (dy > 0) variant1.add(DirKey.LEFT.point) else variant1.add(DirKey.RIGHT.point) }
            variant1.add(DirKey.A.point)

            if (nonePoint == DirKey.NONE.point) {
                if (point == DirKey.LEFT.point && variant1[0] == DirKey.UP.point) {
                    variant1.clear()
                }
                if (point == DirKey.A.point && variant1[0] == DirKey.LEFT.point && variant1[1] == DirKey.LEFT.point) {
                    variant1.clear()
                }
                if (point == DirKey.UP.point && variant1[0] == DirKey.LEFT.point) {
                    variant1.clear()
                }
            }

            val variant2 = mutableListOf<PointX>()
            repeat(abs(dy)) { if (dy > 0) variant2.add(DirKey.LEFT.point) else variant2.add(DirKey.RIGHT.point) }
            repeat(abs(dx)) { if (dx > 0) variant2.add(DirKey.UP.point) else variant2.add(DirKey.DOWN.point) }
            variant2.add(DirKey.A.point)

            if (nonePoint == DirKey.NONE.point) {
                if (point == DirKey.LEFT.point && variant2[0] == DirKey.UP.point) {
                    variant2.clear()
                }
                if (point == DirKey.A.point && variant2[0] == DirKey.LEFT.point && variant2[1] == DirKey.LEFT.point) {
                    variant2.clear()
                }
                if (point == DirKey.UP.point && variant2[0] == DirKey.LEFT.point) {
                    variant2.clear()
                }
            }

            val result1 = calculation(DirKey.A.point, DirKey.NONE.point, variant1, level + 1, maxLevel)
            val result2 = calculation(DirKey.A.point, DirKey.NONE.point, variant2, level + 1, maxLevel)

            val dd = if (result1 != null && result2 == null) {
                result1
            } else if (result1 == null && result2 != null) {
                result2
            } else if ((result1?.size ?: Int.MAX_VALUE) > (result2?.size ?: Int.MAX_VALUE)) {
                result2
            } else {
                result1
            }

            result.addAll(dd!!)

            point = newPoint
        }
//        println("${result.map { p -> DirKey.entries.first { it.point == p }.char }}")
        return result
    }

//    private fun calculate(input: String): List<Char> {

//        val numResult = calculateNum(input)
//        println(numResult)

//        val dirResult1 = calculateDir1(numResult)
//        println(dirResult1)

//        val dirResult2 = calculateDir(dirResult1)
//        println(dirResult2)

//        return numResult
//    }

    private fun calculateNum(input: String): List<Char> {
        val result = mutableListOf<Char>()
        var point = NumKey.A.point
        input.map { NumKey.entries.find { numKey -> numKey.char == it }!!.point }.forEach { newPoint ->
            val dx = point.x - newPoint.x
            val dy = point.y - newPoint.y

            val variant1 = mutableListOf<Char>()
            if (dy > 0) repeat(dy) { variant1.add(DirKey.LEFT.char) } else repeat(abs(dy)) { variant1.add(DirKey.RIGHT.char) }
            if (dx > 0) repeat(dx) { variant1.add(DirKey.UP.char) } else repeat(abs(dx)) { variant1.add(DirKey.DOWN.char) }
            variant1.add(DirKey.A.char)

            val variant2 = mutableListOf<Char>()
            if (dx > 0) repeat(dx) { variant2.add(DirKey.UP.char) } else repeat(abs(dx)) { variant2.add(DirKey.DOWN.char) }
            if (dy > 0) repeat(dy) { variant2.add(DirKey.LEFT.char) } else repeat(abs(dy)) { variant2.add(DirKey.RIGHT.char) }
            variant2.add(DirKey.A.char)

            val result1 = calculateDir1(variant1)
            val result2 = calculateDir1(variant2)

            println("${result1.size} === ${result2.size}")

            if (result1.size > result2.size) result.addAll(result2) else result.addAll(result1)

            point = newPoint
        }
        return result
    }

    private fun calculateDir1(input: List<Char>): List<Char> {
        val result = mutableListOf<Char>()
        var point = DirKey.A.point
        input.map { DirKey.entries.find { dirKey -> dirKey.char == it }!!.point }.forEach { newPoint ->
            val dx = point.x - newPoint.x
            val dy = point.y - newPoint.y

            val variant1 = mutableListOf<Char>()
            if (dy > 0) repeat(dy) { variant1.add(DirKey.LEFT.char) } else repeat(abs(dy)) { variant1.add(DirKey.RIGHT.char) }
            if (dx > 0) repeat(dx) { variant1.add(DirKey.UP.char) } else repeat(abs(dx)) { variant1.add(DirKey.DOWN.char) }
            variant1.add(DirKey.A.char)

            val variant2 = mutableListOf<Char>()
            if (dx > 0) repeat(dx) { variant2.add(DirKey.UP.char) } else repeat(abs(dx)) { variant2.add(DirKey.DOWN.char) }
            if (dy > 0) repeat(dy) { variant2.add(DirKey.LEFT.char) } else repeat(abs(dy)) { variant2.add(DirKey.RIGHT.char) }
            variant2.add(DirKey.A.char)

            val result1 = calculateDir2(variant1)
            val result2 = calculateDir2(variant2)

            println("${result1.size} === ${result2.size}")

            if (result1.size > result2.size) result.addAll(result2) else result.addAll(result1)

            point = newPoint
        }
        return result
    }

    private fun calculateDir2(input: List<Char>): List<Char> {
        val result = mutableListOf<Char>()
        var point = DirKey.A.point
        input.map { DirKey.entries.find { dirKey -> dirKey.char == it }!!.point }.forEach { newPoint ->
            val dx = point.x - newPoint.x
            val dy = point.y - newPoint.y
            if (dy > 0) repeat(dy) { result.add(DirKey.LEFT.char) } else repeat(abs(dy)) { result.add(DirKey.RIGHT.char) }
            if (dx > 0) repeat(dx) { result.add(DirKey.UP.char) } else repeat(abs(dx)) { result.add(DirKey.DOWN.char) }
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