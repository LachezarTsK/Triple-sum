import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Solution {

	public static void main(String[] args) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 512);

		int size_elements_A = Integer.parseInt(stringTokenizer.nextToken());
		int size_elements_B = Integer.parseInt(stringTokenizer.nextToken());
		int size_elements_C = Integer.parseInt(stringTokenizer.nextToken());

		Set<Integer> set_A = new TreeSet<Integer>();
		Set<Integer> set_B = new TreeSet<Integer>();
		Set<Integer> set_C = new TreeSet<Integer>();

		stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		for (int i = 0; i < size_elements_A; i++) {
			set_A.add(Integer.parseInt(stringTokenizer.nextToken()));
		}

		stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		for (int i = 0; i < size_elements_B; i++) {
			set_B.add(Integer.parseInt(stringTokenizer.nextToken()));
		}

		stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		for (int i = 0; i < size_elements_C; i++) {
			set_C.add(Integer.parseInt(stringTokenizer.nextToken()));
		}
		bufferedReader.close();

		long result = find_numberOfSpecialTriplets(set_A, set_B, set_C);
		bufferedWriter.write(result + "\n");

		bufferedWriter.flush();
		bufferedWriter.close();
	}

	/**
	 * The method finds the number of unique special triplets, i.e. the middle
	 * element is always equal to, or greater than the first and last element.
	 * 
	 * Binary search is applied in order to avoid iteration through list_A and
	 * list_C.
	 * 
	 * At each iteration through list_B, a smaller and smaller part of list_A and
	 * list_B is searched.
	 * 
	 * @return A long value, representing the total number of special triplets.
	 */
	public static long find_numberOfSpecialTriplets(Set<Integer> set_A, Set<Integer> set_B, Set<Integer> set_C) {

		List<Integer> list_A = new ArrayList<Integer>(set_A);
		List<Integer> list_B = new ArrayList<Integer>(set_B);
		List<Integer> list_C = new ArrayList<Integer>(set_C);

		int index_A = list_A.size() - 1;
		int index_C = list_C.size() - 1;
		long total_specialTriplets = 0;

		for (int i = list_B.size() - 1; i >= 0; i--) {
			int current = list_B.get(i);
			index_A = binarySearch_findValue_equalTo_or_immediatelyBelow_Key(list_A.subList(0, index_A + 1), current);
			index_C = binarySearch_findValue_equalTo_or_immediatelyBelow_Key(list_C.subList(0, index_C + 1), current);
			total_specialTriplets += (long) (index_A + 1) * (index_C + 1);
		}
		return total_specialTriplets;
	}

	/**
	 * The method applies binary search to find the element with value that is equal
	 * to, or immediately below, the key.
	 * 
	 * @return The index of this element. If there are no elements with value that
	 *         is equal to, or less than, the key, then "-1" is returned.
	 */
	private static int binarySearch_findValue_equalTo_or_immediatelyBelow_Key(List<Integer> list, int key) {
		int lowerLimit = 0;
		int upperLimit = list.size() - 1;

		while (lowerLimit <= upperLimit) {
			int middle = lowerLimit + (upperLimit - lowerLimit) / 2;
			if (list.get(middle) == key) {
				return middle;
			}
			if (list.get(middle) < key) {
				lowerLimit = middle + 1;
			} else {
				upperLimit = middle - 1;
			}
		}
		return upperLimit;
	}
}
