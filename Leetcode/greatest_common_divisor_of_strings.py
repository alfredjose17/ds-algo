"""
For two strings s and t, we say "t divides s" if and only if s = t + ... + t (i.e., t is concatenated with itself one or more times).

Given two strings str1 and str2, return the largest string x such that x divides both str1 and str2.

 

Example 1:

Input: str1 = "ABCABC", str2 = "ABC"
Output: "ABC"
Example 2:

Input: str1 = "ABABAB", str2 = "ABAB"
Output: "AB"
Example 3:

Input: str1 = "LEET", str2 = "CODE"
Output: ""
 

Constraints:

1 <= str1.length, str2.length <= 1000
str1 and str2 consist of English uppercase letters.
"""

def gcdOfStrings(str1, str2):
    """
    :type str1: str
    :type str2: str
    :rtype: str
    """

    len1, len2 = len(str1), len(str2)

    if not len1 or not len2:
        return ""

    for i in range(min(len1, len2), 0, -1):
        if str2[:i]*(len2//i) == str2 and str2[:i]*(len1//i) == str1:
            return str2[:i]

    return ""

print(gcdOfStrings("ABABAB", "ABAB"))