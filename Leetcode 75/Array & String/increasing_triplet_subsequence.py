"""
Given an integer array nums, return true if there exists a triple of indices (i, j, k) such that i < j < k and nums[i] < nums[j] < nums[k]. If no such indices exists, return false.

Example 1:

Input: nums = [1,2,3,4,5]
Output: true
Explanation: Any triplet where i < j < k is valid.
Example 2:

Input: nums = [5,4,3,2,1]
Output: false
Explanation: No triplet exists.
Example 3:

Input: nums = [2,1,5,0,4,6]
Output: true
Explanation: The triplet (3, 4, 5) is valid because nums[3] == 0 < nums[4] == 4 < nums[5] == 6.
 

Constraints:

1 <= nums.length <= 5 * 105
-231 <= nums[i] <= 231 - 1
"""

def increasingTriplet(nums):
        
    n = len(nums)

    if n < 3:
        return False

    leftMin = [nums[0]]
    rightMax = [nums[-1]]

    for i in range(1, n):
        if nums[i] < leftMin[-1]:
            leftMin.append(nums[i])
        else:
            leftMin.append(leftMin[-1])
        if nums[n-i-1] > rightMax[-1]:
            rightMax.append(nums[n-i-1])
        else:
            rightMax.append(rightMax[-1])

    rightMax = rightMax[::-1]

    for i in range(1, n-1):
        if nums[i] > leftMin[i] and nums[i] < rightMax[i]:
            return True
        
    return False

print(increasingTriplet([2,1,5,0,4,6]))