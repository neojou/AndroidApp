import java.util.Arrays

class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        var m = HashMap<Int, Int>()
        var res = mutableListOf<Int>()

        var i = 0
        while (i < nums.size) {
            var pos : Int? = m.get(target - nums[i])
            if (pos == null) {
                m.set(nums[i], i)
            } else {
                res.add(pos);
                res.add(i);
                break;
            }
            i++
        }

        return res.toIntArray();
    }
}

fun main() {
    var s = Solution()
    var nums = intArrayOf(2, 7, 11, 15)
    var results = s.twoSum(nums, 9)
    println(Arrays.toString(results))

}
