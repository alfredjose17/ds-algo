"""
You have a long flowerbed in which some of the plots are planted, and some are not. However, flowers cannot be planted in adjacent plots.

Given an integer array flowerbed containing 0's and 1's, where 0 means empty and 1 means not empty, and an integer n, return true if n new flowers can be planted in the flowerbed without violating the no-adjacent-flowers rule and false otherwise.

 

Example 1:

Input: flowerbed = [1,0,0,0,1], n = 1
Output: true
Example 2:

Input: flowerbed = [1,0,0,0,1], n = 2
Output: false
 

Constraints:

1 <= flowerbed.length <= 2 * 104
flowerbed[i] is 0 or 1.
There are no two adjacent flowers in flowerbed.
0 <= n <= flowerbed.length
"""

def canPlaceFlowers(flowerbed, n):
    """
    :type flowerbed: List[int]
    :type n: int
    :rtype: bool
    """

    # no flowers to plant
    if n == 0:
        return True

    # length is 1
    if len(flowerbed) == 1:
        if flowerbed[0] == 0:
            return True
        else:
            return False

    flowerbed = [0] + flowerbed + [0]

    i = 1
    while i < len(flowerbed)-1:
        if not flowerbed[i]:
            if not flowerbed[i-1] and not flowerbed[i+1]:
                flowerbed[i] = 1
                n -= 1
        else:
            i += 1
        i += 1

    if n <= 0:
        return True
    else:
        return False
        
print(canPlaceFlowers([1,0,0,0,1], 1))
print(canPlaceFlowers([1,0,0,0,1], 2))