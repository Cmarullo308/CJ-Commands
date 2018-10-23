package Tools;

public class BaseConverter {
	public static String convert(String num, int fromBase, int toBase) {
		if (fromBase == 10) { // 10 to any
			return Integer.toString(Integer.parseInt(num), toBase);
		} else if (toBase == 10) { // any to 10
			return Integer.parseInt(num, fromBase) + "";
		} else { //Any base to any base
			return Integer.toString(Integer.parseInt(convert(num, fromBase, 10)), toBase);
		}
	}
}