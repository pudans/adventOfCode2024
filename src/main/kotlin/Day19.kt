package day01

import java.io.File

class Day19 : Base<Day19.Data, Long>(19) {

    data class Data(
        val patterns: List<String>,
        val designs: List<String>
    )

    override fun part1(input: Data): Long = input.designs.fold(0) { r, design ->
        r + if (isDesignMatched(input.patterns, design) > 0L) 1 else 0
    }

    private fun isDesignMatched(patterns: List<String>, design: String): Long {
        val lengthWays = mutableMapOf(0 to 1L)
        for (patternEnd in 1..design.length) {
            val possiblePatternStarts = patterns.mapNotNull { pattern ->
                val patternStart = patternEnd - pattern.length
                lengthWays[patternStart]?.takeIf { design.substring(patternStart, patternEnd) == pattern }
            }
            lengthWays[patternEnd] = possiblePatternStarts.sum()
        }
        return lengthWays[design.length]!!
    }

    override fun part2(input: Data): Long = input.designs.fold(0L) { r, design ->
        r + isDesignMatched(input.patterns, design)
    }

    override fun mapInputData(file: File): Data {
        val lines = file.readLines()
        return Data(
            patterns = lines.first().split(", "),
            designs = lines.drop(2)
        )
    }
}

fun main() {
    Day19().submitPart1TestInput() // 6
    Day19().submitPart1Input() // 298
    Day19().submitPart2TestInput() // 16
    Day19().submitPart2Input() // 572248688842069
}