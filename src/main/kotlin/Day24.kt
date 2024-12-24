package day01

import day01.Day24.Operation.*
import java.io.File

class Day24 : Base<Day24.Data, Int>(24) {

    data class Data(
        val initialMap: Map<String, Boolean>,
        val operations: Map<String, Connection>
    )

    enum class Operation(val value: String) { AND("AND"), OR("OR"), XOR("XOR") }

    private fun String.toOperation(): Operation = Operation.entries.first { it.value == this }

    private fun Operation.invoke(value1: Boolean, value2: Boolean): Boolean = when (this) {
        AND -> value1 && value2
        OR -> value1 || value2
        XOR -> value1 xor value2
    }

    data class Connection(
        val value1: String,
        val value2: String,
        val operation: Operation
    )

    override fun part1(input: Data): Int {
        println(input)
        return 0
    }

    override fun part2(input: Data): Int = 0

    override fun mapInputData(file: File): Data {
        val initialMap = mutableMapOf<String, Boolean>()
        val operations = mutableMapOf<String, Connection>()
        val lines = file.readLines()
        var isOperations = false

        lines.forEach { line ->
            if (line.isEmpty()) {
                isOperations = true
            } else if (!isOperations) {
                line.split(": ").let { initialMap[it.first()] = it[1] == "1"}
            } else {
                line.split(" ").let {
                    operations[it.last()] = Connection(
                        value1 = it[0], value2 = it[2], operation = it[1].toOperation()
                    )
                }
            }
        }
        return Data(initialMap, operations)
    }

}

fun main() {
    Day24().submitPart1TestInput() //
//    Day24().submitPart1Input() //
//    Day24().submitPart2TestInput() //
//    Day24().submitPart2Input() //
}