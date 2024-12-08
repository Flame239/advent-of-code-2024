fun main() {
    data class D(val i: Int, val j: Int)

    val map by lazy {
        readInput("Day8").map { it.toCharArray() }
    }

    val n = map.size
    val m = map[0].size

    val antennas by lazy {
        val res = HashMap<Char, MutableList<D>>()
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (map[i][j] != '.') {
                    res.computeIfAbsent(map[i][j]) { mutableListOf() }.add(D(i, j))
                }
            }
        }
        res
    }

    fun getAntinodes(a1: D, a2: D): Set<D> {
        val iDiff = a1.i - a2.i
        val jDiff = a1.j - a2.j
        return setOf(D(a1.i + iDiff, a1.j + jDiff), D(a2.i - iDiff, a2.j - jDiff))
    }

    fun getMoreAntinodes(a1: D, a2: D): Set<D> {
        val iDiff = a1.i - a2.i
        val jDiff = a1.j - a2.j
        val res = mutableSetOf(a1, a2)

        var curI = a1.i + iDiff
        var curJ = a1.j + jDiff
        while (curI in 0 until n && curJ in 0 until m) {
            res.add(D(curI, curJ))
            curI += iDiff
            curJ += jDiff
        }

        curI = a2.i - iDiff
        curJ = a2.j - jDiff
        while (curI in 0 until n && curJ in 0 until m) {
            res.add(D(curI, curJ))
            curI -= iDiff
            curJ -= jDiff
        }

        return res
    }

    fun part1(): Int {
        val allAntinodes = mutableSetOf<D>()
        antennas.forEach { (_, nodes) ->
            for (i in nodes.indices) {
                for (j in i + 1 until nodes.size) {
                    allAntinodes.addAll(getAntinodes(nodes[i], nodes[j]))
                }
            }
        }

        return allAntinodes.filter { (i, j) -> i in 0 until n && j in 0 until m }.size
    }

    fun part2(): Int {
        val allAntinodes = mutableSetOf<D>()
        antennas.forEach { (_, nodes) ->
            for (i in nodes.indices) {
                for (j in i + 1 until nodes.size) {
                    allAntinodes.addAll(getMoreAntinodes(nodes[i], nodes[j]))
                }
            }
        }

        return allAntinodes.filter { (i, j) -> i in 0 until n && j in 0 until m }.size
    }

    measure { part1() }
    measure { part2() }
}
