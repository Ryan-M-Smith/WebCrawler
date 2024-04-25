//
// FILENAME: Main.java | Web Crawler
// GROUP: Ryan, Simon, Suyog
// DESCRIPTION: The main function
// CREATED: 2024-04-21 @ 4:23 AM
//

package webcrawler;

import gui.App;

public class Main {
	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("-X")) {
			// Launch the desktop app
			App app = new App();
			app.launch();
		}
		else {
			// Run the CLI
			WebCrawler crawler = new WebCrawler();
			crawler.runCLI();
		}
	}
}