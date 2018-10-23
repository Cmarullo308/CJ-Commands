package Tools;

public class MyFuncs {
	public enum Sort {
		Ascending, Descending;
	}

	// ----------Char Class----------

	public static class Chars {
		public static char switchCase(char c) {
			if (c >= 65 && c <= 90) { // if Uppercase
				return c += 32;
			} else if (c >= 97 && c <= 122) { // if Lowercase
				return c -= 32;
			}

			return c;
		}

		public static char toLower(char c) {
			if (c >= 65 && c <= 90) {
				return c += 32;
			}
			return c;
		}

		public static char toUpper(char c) {
			if (c >= 97 && c <= 122) {
				return c -= 32;
			}
			return c;
		}

		public static void reverseCharArray(char[] c) {
			char temp;

			for (int i = 0; i < c.length / 2; i++) {
				temp = c[i];
				c[i] = c[c.length - 1 - i];
				c[c.length - 1 - i] = temp;
			}
		}

	}

	// ----------Random Class----------

	public static class Random {
		public static int randomIntBetween(int min, int max) {
			return new java.util.Random().nextInt(max - min + 1) + min;
		}

		public static double randomDoubleBetween(double min, double max) {
			return min + (max - min) * new java.util.Random().nextDouble();
		}

		public static double randomDoubleBetween(double min, double max, int decimalPoints) {
			return Double.parseDouble(
					String.format("%." + decimalPoints + "f", min + (max - min) * new java.util.Random().nextDouble()));
		}
	}

	// ----------Strings Class----------

	public static class Strings {
		public static String reverseChars(String str) {
			char[] charArr = new char[str.length()];

			for (int i = 0; i < str.length(); i++) {
				charArr[str.length() - 1 - i] = str.charAt(i);
			}

			return String.valueOf(charArr);
		}

		public static String addLeadingZeros(String num, int size) {
			String zeros = "";
			int zerosNeeded = size - num.length();

			while (zeros.length() < zerosNeeded) {
				zeros += "0";
			}

			return zeros + num;
		}

		/**
		 * Sorts the string array by length
		 * 
		 * @param strArr
		 */
		public static void sortArrayByLength(String[] strArr, Sort sort) {
			if (sort == Sort.Ascending) {
				quickSortASEC(strArr, 0, strArr.length - 1);
			} else if (sort == Sort.Descending) {
				quickSortDESC(strArr, 0, strArr.length - 1);
			}
		}

		/**
		 * Quicksorts the String arry by length accending
		 * 
		 * @param strArr
		 * @param low
		 * @param high
		 */
		private static void quickSortASEC(String[] strArr, int low, int high) {
			int i = low;
			int j = high;
			String pivot = strArr[low + (high - low) / 2];
			while (i <= j) {
				while (strArr[i].length() < pivot.length()) {
					i++;
				}
				while (strArr[j].length() > pivot.length()) {
					j--;
				}
				if (i <= j) {
					exchangeNumbers(strArr, i, j);
					i++;
					j--;
				}
			}
			if (low < j)
				quickSortASEC(strArr, low, j);
			if (i < high)
				quickSortASEC(strArr, i, high);
		}

		/**
		 * Quicksorts the String arry by length decending
		 * 
		 * @param strArr
		 * @param low
		 * @param high
		 */
		private static void quickSortDESC(String[] strArr, int low, int high) {
			int i = low;
			int j = high;
			String pivot = strArr[(low + high) / 2];

			while (i < j) {
				while (strArr[i].length() > pivot.length()) {
					i++;
				}
				while (strArr[j].length() < pivot.length()) {
					j--;
				}
				if (i <= j) {
					exchangeNumbers(strArr, i, j);
					i++;
					j--;
				}
			}

			if (low < j) {
				quickSortDESC(strArr, low, j);
			}
			if (i < high) {
				quickSortDESC(strArr, i, high);
			}
		}

		private static void exchangeNumbers(String[] strArr, int i, int j) {
			String temp = strArr[i];
			strArr[i] = strArr[j];
			strArr[j] = temp;
		}

		/**
		 * 
		 * @param string
		 *            The string to search
		 * @param needle
		 *            What is being searched for in the strinf
		 * @return True if the substring is found
		 */
		public static boolean containsIgnoreCase(String string, String needle) {
			for (int i = 0; i < string.length() - needle.length() + 1; i++) {
				if (string.substring(i, i + needle.length()).equalsIgnoreCase(needle)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * 
		 * @param string
		 *            The string to search
		 * @param needle
		 *            What is being searched for in the strinf
		 * @return True if the substring is found
		 */
		public static boolean contains(String string, String needle) {
			for (int i = 0; i < string.length() - needle.length() + 1; i++) {
				if (string.substring(i, i + needle.length()).equals(needle)) {
					return true;
				}
			}
			return false;
		}
	}

	// ----------Arrays Class----------

	public static class Arrays {
		public static <T> void reverseArray(T[] arr) {
			T temp;
			for (int i = 0; i < arr.length / 2; i++) {
				temp = arr[i];
				arr[i] = arr[arr.length - 1 - i];
				arr[arr.length - 1 - i] = temp;
			}
		}
	}
}
