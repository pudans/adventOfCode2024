package day01

import java.io.File
import kotlin.math.pow

class Day17 : Base<Day17.Data, String>(17) {

    data class Program(val opcode: Int, val operand: Int)
    data class Data(val a: Long, val b: Long, val c: Long, val programs: List<Program>)

    override fun part1(input: Data): String {
        var a = input.a
        var b = input.b
        var c = input.c
        val output = mutableListOf<Int>()

        var i = 0
        while (i in input.programs.indices) {
            val program = input.programs[i]
            val realOperand: Long = when (program.operand) {
                4 -> a
                5 -> b
                6 -> c
                else -> program.operand.toLong()
            }
            when (program.opcode) {
                0 -> { // adv
                    a = (a / 2.0.pow(realOperand.toDouble())).toLong()
                }
                1 -> { // bxl
                    b = b.xor(realOperand)
                }
                2 -> { // bst
                    b = realOperand % 8
                }
                3 -> { // jnz
                    if (a != 0L) {
                        i = (realOperand / 2).toInt()
                        continue
                    }
                }
                4 -> { // bxc
                    b = b.xor(c)
                }
                5 -> { // out
                    output.add((realOperand % 8).toInt())
                }
                6 -> { // bdv
                    b = (a / 2.0.pow(realOperand.toDouble())).toLong()
                }
                7 -> { // cdv
                    c = (a / 2.0.pow(realOperand.toDouble())).toLong()
                }
                else -> Unit
            }
            i++
        }

        println("a=$a b=$b c=$c")

        return output.joinToString(separator = ",") { it.toString() }
    }

    override fun part2(input: Data): String {
        return ""
    }

    override fun mapInputData(file: File): Data {
        val lines = file.readLines()
        return Data(
            a = lines[0].removePrefix("Register A: ").toLong(),
            b = lines[1].removePrefix("Register B: ").toLong(),
            c = lines[2].removePrefix("Register C: ").toLong(),
            programs = lines[4].removePrefix("Program: ").toCharArray().filter { it.isDigit() }
                .map { it.toString().toInt() }.chunked(2) { Program(it[0], it[1]) }
        )
    }
}

fun main() {
    Day17().submitPart1TestInput() // 4,6,3,5,6,3,5,2,1,0
    Day17().submitPart1Input() // 6,6,3,6,7,4,1,5,0 - Failed
//    Day17().submitPart2TestInput() //
//    Day17().submitPart2Input() //
}