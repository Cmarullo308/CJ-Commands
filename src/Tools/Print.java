package Tools;

public class Print {
	public static <T> void p(T thing) {
		System.out.print(thing);
	}

	public static <T> void pl(T thing) {
		System.out.println(thing);
	}

	public static void endl() {
		System.out.println();
	}

	public static void endl(int numberOfLines) {
		for (int i = 0; i < numberOfLines; i++) {
			System.out.println();
		}
	}
}
