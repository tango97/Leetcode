/*
Given a string, find the length of the longest substring without repeating characters. For example, the longest substring without repeating letters for "abcabcbb" is "abc", which the length is 3. For "bbbbb" the longest substring is "b", with the length of 1.
*/

import java.util.*;
public class LongestSubstringWithoutRepeatingCharacters {
	public static void main(String[] args) {
		System.out.print(new LongestSubstringWithoutRepeatingCharacters().lengthOfLongestSubstring4("pwwkew"));
	}
	
	// rank 2/3
	public int lengthOfLongestSubstring(String s) {
		if (s == null || s.length() == 0)
			return 0;
		Set<Character> hs = new HashSet<>();
		int start = 0, res = 0;								// two pointers, start point to the never-exist character
		
		for (int i = 0; i < s.length(); i++) {
			if (hs.contains(s.charAt(i))) {
				while (start != i && s.charAt(start) != s.charAt(i)) {			// while loop, until start == i
					hs.remove(s.charAt(start));
					start++;
				}
				start++;
			}
			else {
				hs.add(s.charAt(i));
				res = Math.max(res, i - start + 1);
			}
		}
		return res;
	}
	
	// Rank 3/3		hm.clear() always clear and put, not good. 
	public int lengthOfLongestSubstring2(String s) {
		if (s == null || s.length() == 0)
			return 0;
		Map<Character, Integer> hm = new HashMap<>();
		int res = 0;
		for (int i = 0; i < s.length(); i++) {
			if (hm.containsKey(s.charAt(i))) {
				res = Math.max(res, hm.size());
				i = hm.get(s.charAt(i));					// i in the for loop is also the pointer
				hm.clear();
			}
			else {
				hm.put(s.charAt(i), i);
			}
		}
		return Math.max(res, hm.size());
	}
	
	// Rank 1/3		use flag to record visited, boolean[character's ASCII] == visited?		Beat 97% 
	public int lengthOfLongestSubstring3(String s) {
		boolean[] flag = new boolean[256];
		int res = 0, start = 0;
		char[] arr = s.toCharArray();
		
		for (int i = 0; i < arr.length; i++) {
			char ch = arr[i];
			if (flag[ch]) {
				res = Math.max(res, i - start);
				for (int j = start; j < i; j++) {				// find the replicate, set the new start
					if (arr[j] == ch) {
						start = j + 1;
						break;
					}
					flag[arr[j]] = false;
				}
			}
			else {
				flag[ch] = true;
			}
		}
		return Math.max(res, arr.length - start);
	}
	
	// 78%. occ[ch] = the last occur index. complicate 
	// http://www.jiuzhang.com/solutions/longest-substring-without-repeating-characters/
	// http://blog.csdn.net/likecool21/article/details/10858799
	
	public int lengthOfLongestSubstring4(String s) {
		if(s == null || s.length() == 0)
			return 0;
		int[] occ = new int[256];
		Arrays.fill(occ, -1);
				
		int res = 0, start = 0;
		occ[s.charAt(0)] = 0;
		for (int i = 1; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (occ[ch] >= start) {
				res = Math.max(res, i - start);
				start = occ[ch] + 1;
			}
			occ[ch] = i;
		}
		return Math.max(res, s.length() - start);
	}
}
