"""
Given a string s, reverse only all the vowels in the string and return it.

The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in both lower and upper cases, more than once.

 

Example 1:

Input: s = "hello"
Output: "holle"
Example 2:

Input: s = "leetcode"
Output: "leotcede"
 

Constraints:

1 <= s.length <= 3 * 105
s consist of printable ASCII characters.
"""

def reverseVowels(s: str) -> str:

    if len(s) == 1:
        return s

    pos = []
    result = list(s[:])
    count = 0

    for i in range(len(s)):
        if s[i].lower() in ['a', 'e', 'i', 'o', 'u']:
            pos.append(i)
            count += 1

    for i in range(count):
        result[pos[i]] = s[pos[count-i-1]]

    return "".join(result)

print(reverseVowels("leetcode"))