fun main() {
    val map: List<CharArray> by lazy {
        readInput("Day6").map { it.toCharArray() }
    }

    data class D(val i: Int, val j: Int)

    val DIRS = listOf(D(-1, 0), D(0, 1), D(1, 0), D(0, -1))

    var startI = 0
    var startJ = 0
    outer@ for (i in map.indices) {
        for (j in map[0].indices) {
            if (map[i][j] == '^') {
                startI = i
                startJ = j
                break@outer
            }
        }
    }

    fun originalPath(): MutableSet<D> {
        var i = startI
        var j = startJ
        var dir = 0
        val visited = mutableSetOf<D>()
        while (true) {
            visited.add(D(i, j))
            val nextI = i + DIRS[dir].i
            val nextJ = j + DIRS[dir].j
            if (nextI !in map.indices || nextJ !in map[0].indices) break

            if (map[nextI][nextJ] == '#') {
                dir = (dir + 1) % 4
            } else {
                i = nextI
                j = nextJ
            }
        }
        return visited
    }

    fun part1(): Int = originalPath().size

    fun part2(): Int {
        // block should be somewhere on the original path
        // just bruteforce all possible places
        val path = originalPath()
        path.remove(D(startI, startJ))

        data class State(val i: Int, val j: Int, val dir: Int)

        return path.count { d ->
            val curMap = map.map { it.copyOf() }.apply { this[d.i][d.j] = '#' }
            var i = startI
            var j = startJ
            var dir = 0
            val visited = mutableSetOf<State>()
            while (true) {
                val curState = State(i, j, dir)
                // looking for cycle
                if (!visited.add(curState)) return@count true
                val nextI = i + DIRS[dir].i
                val nextJ = j + DIRS[dir].j
                if (nextI !in map.indices || nextJ !in map[0].indices) return@count false

                if (curMap[nextI][nextJ] == '#') {
                    dir = (dir + 1) % 4
                } else {
                    i = nextI
                    j = nextJ
                }
            }
            true
        }
    }

    measure { part1() }
    measure { part2() }
}
