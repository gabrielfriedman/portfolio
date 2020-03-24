package uitextlib;

public class Configs {
	public static int SCREEN_WIDTH = 80;
	
	public static String BORDER_STRING = "=";
	
	public static void newBorder() {
		for (int i = 0; i < SCREEN_WIDTH; i++) {
			System.out.print(BORDER_STRING);
		}
		System.out.println();
	}
	
	
}