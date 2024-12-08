fun main() {

    data class Eq(val res: Long, val nums: LongArray)

    val input by lazy {
        readInput("Day7").map {
            val (res, nums) = it.split(": ")
            Eq(res.toLong(), nums.split(" ").map { it.toLong() }.toLongArray())
        }
    }

    fun canBeSolved(target: Long, nums: LongArray, i: Int): Boolean {
        if (i == 0) return target == nums[0]
        if (nums[i] > target) return false

        var ans = canBeSolved(target - nums[i], nums, i - 1)
        if (target % nums[i] == 0L) {
            ans = ans || canBeSolved(target / nums[i], nums, i - 1)
        }
        return ans
    }

    fun canBeSolved2(target: Long, nums: LongArray, i: Int): Boolean {
        if (i == 0) return target == nums[0]
        if (nums[i] > target) return false

        var ans = canBeSolved2(target - nums[i], nums, i - 1)
        if (target % nums[i] == 0L) {
            ans = ans || canBeSolved2(target / nums[i], nums, i - 1)
        }

        // ex. target = 486 and nums[i - 1] = 6 -> new target = 48
        val targetS = target.toString()
        val lastNumS = nums[i].toString()
        if (targetS.endsWith(lastNumS)) {
            val newTarget = targetS.removeSuffix(lastNumS).toLong()
            ans = ans || canBeSolved2(newTarget, nums, i - 1)
        }
        return ans
    }

    fun part1(): Long = input.filter { (res, nums) -> canBeSolved(res, nums, nums.lastIndex) }.sumOf { it.res }

    fun part2(): Long = input.filter { (res, nums) -> canBeSolved2(res, nums, nums.lastIndex) }.sumOf { it.res }

    measure { part1() }
    measure { part2() }
}
