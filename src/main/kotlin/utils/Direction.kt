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

fun Direction.turnRight(): Direction =
    when (this) {
        Direction.RIGHT -> Direction.DOWN
        Direction.UP -> Direction.RIGHT
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
    }