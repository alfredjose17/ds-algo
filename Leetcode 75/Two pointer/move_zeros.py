"""
Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.

Note that you must do this in-place without making a copy of the array.

 

Example 1:

Input: nums = [0,1,0,3,12]
Output: [1,3,12,0,0]
Example 2:

Input: nums = [0]
Output: [0]
 

Constraints:

1 <= nums.length <= 104
-231 <= nums[i] <= 231 - 1
"""

def moveZeroes(nums):
    """
    Do not return anything, modify nums in-place instead.
    """
    
    if len(nums) == 1:
        return 0

    i, j = 0, 1

    while j < len(nums):
        if not nums[i]:
            if nums[j]:
                temp = nums[i]
                nums[i] = nums[j]
                nums[j] = temp
            else:
                j += 1
                continue
        i += 1
        j += 1


nums = [0,1,0,3,12]
moveZeroes(nums)
print(nums)