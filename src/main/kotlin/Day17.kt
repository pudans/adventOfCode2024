package day01

import java.io.File

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
                    a = a.shr(realOperand.toInt())
                }
                1 -> { // bxl
                    b = b.xor(program.operand.toLong())
                }
                2 -> { // bst
                    b = realOperand.mod(8).toLong()
                }
                3 -> { // jnz
                    if (a != 0L) {
                        i = (program.operand / 2)
                        continue
                    }
                }
                4 -> { // bxc
                    b = b.xor(c)
                }
                5 -> { // out
                    output.add(realOperand.mod(8))
                }
                6 -> { // bdv
                    b = a.shr(realOperand.toInt())
                }
                7 -> { // cdv
                    c = a.shr(realOperand.toInt())
                }
                else -> Unit
            }
            i++
        }
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
    Day17().submitPart1Input() // 2,7,2,5,1,2,7,3,7
//    Day17().submitPart2TestInput() //
//    Day17().submitPart2Input() //
}