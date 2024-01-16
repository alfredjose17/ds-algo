"""
Given two strings s and t, return true if s is a subsequence of t, or false otherwise.

A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).


Example 1:

Input: s = "abc", t = "ahbgdc"
Output: true
Example 2:

Input: s = "axc", t = "ahbgdc"
Output: false
 

Constraints:

0 <= s.length <= 100
0 <= t.length <= 104
s and t consist only of lowercase English letters.
"""

def isSubsequence(self, s: str, t: str) -> bool:

        len1 = len(s)
        len2 = len(t)

        if not len1:
            return True

        if len2 < len1:
            return False
        
        i, j = 0, 0

        while i < len1 and j < len2:
            if s[i] == t[j]:
                i += 1
            j += 1

        return i == len1