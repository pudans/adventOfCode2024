package day01.utils

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    companion object {
        fun Char.fromSign(): Direction = when (this) {
            '^' -> UP
            '<' -> LEFT
            '>' -> RIGHT
            else -> DOWN
        }
    }
}