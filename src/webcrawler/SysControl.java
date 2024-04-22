//
// FILENAME: SysControl.java | Web Crawler
// GROUP: Ryan, Simon, Suyog
// DESCRIPTION: Interface with the system to enhace the application experience
// CREATED: 2024-04-22 @ 12:45 AM
//

package webcrawler;

import java.io.IOException;

public final class SysControl {
	private final static String OS 			= System.getProperty("os.name");
	public final static boolean OS_WINDOWS 	= OS.contains("Windows");
	public final static boolean OS_MAC_OS 	= OS.equals("Mac OS X");
	public final static boolean OS_LINUX 	= OS.contains("Linux");

	/**
	 * Clear the console. This function invokes {@code cls} on Windows and invokes {@code clear}
	 * on Unix-like OSes like macOS and Linux.
	 * 
	 * @author Ryan Smith
	 */
	public static void clearConsole() {
		try {
			if (OS_WINDOWS) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			}
			else if (OS_MAC_OS || OS_LINUX) {
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			}
		}
		catch (InterruptedException | IOException e) {
			return;
		}
	}
}