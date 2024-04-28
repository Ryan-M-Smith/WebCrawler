//
// FILENAME: WebCrawler.java | Web Crawler
// GROUP: Ryan, Simon, Suyog
// DESCRIPTION: A web crawler that finds URLs and email addresses
// CREATED: 2024-04-20 @ 2:48 PM
//

package webcrawler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Use regular expressions to locate and log email addresses and URLs within webpages.
 */
public class WebCrawler {
	private final static Pattern EMAIL_PATTERN 	= Pattern.compile("(([\\w\\.]+)@([\\w]+)(([\\.\\w]+))+)");
	private final static Pattern URL_PATTERN 	= Pattern.compile("(https?://[\\w|\\d|.|/]+.[\\w|\\d|.|/]+([\\w|\\d|\\-|=]|(%[0-9&&a-f]{2}))+)");

	//
	// We decided to use an ArrayList ADT to store the emails found while crawling. The emails need to be stored with some
	// semblence of order, so an ArrayList was perfect because it is ordered. To solve the issue of storing previously found
	// emails, we check to see if the ArrayList contains the email and only push the email into the ArrayList if it is not already
	// contained.
	//
	private ArrayList<String> emails = new ArrayList<>();
	
	//
	// We decided to use an ArrayList to store the URLs that have already been visited while crawling. In order to easily display
	// the crawled URLs, it is important for the ADT used to have some sort of order, so the ArrayList is perfect for the task of
	// storing visited URLs.
	//
	private ArrayList<URL> urlsVisited = new ArrayList<>();
	
	//
	// Using a queue ADT to store the URLs that need to be visited while crawling. A Queue is the best choice for this case because it
	// follows the First-In-First-Out (FIFO) principle, which ensures that the URLs are visited in the order they were discovered. This
	// prevents any potential bias or prioritization of certain URLs over others.
	//
	private Queue<URL> urlsToVisit = new ArrayDeque<>();
	
	/**
	 * Get a valid URL as user input
	 * 
	 * @return 	The URL
	 */
	public static URL getValidURL(Scanner scanner) {
		URL url = null;
		boolean validURL;

		do {
			try {
				System.out.print("Enter a URL to start crawling: ");
				String startURL = scanner.next();
				url = new URI(startURL).toURL();
				validURL = true;
			}
			catch (MalformedURLException | URISyntaxException | IllegalArgumentException e) {
				System.err.printf("Error: %s\n\n", e.getMessage());
				validURL = false;
			}
		} while (!validURL);

		return url;
	}

	/**
	 * Crawl {@code url} and keep track of any email addresses and URLs found on the site.
	 * 
	 * @param 	url 			The URL to crawl
	 * @return 					An array of result data. Index 0 is the number of new emails found,
	 * 							index 1 is the number of new URLs found.
	 * 
	 * @throws 	IOException 	{@code url} can't be accessed (usually due to a 404 error)
	 */
	public int[] crawl(URL url) throws IOException {
		Matcher matcher;

		InputStream stream = url.openStream();
		Scanner websiteData = new Scanner(stream);

		int emailsFound = 0, urlsFound = 0;

		while (websiteData.hasNextLine()) {
			String token = websiteData.nextLine();

			if ((matcher = EMAIL_PATTERN.matcher(token)).find() && !emails.contains(matcher.group())) {
				// Match emails and save them
				emails.add(matcher.group());
				emailsFound++;
			}
			else if ((matcher = URL_PATTERN.matcher(token)).find() && !urlsToVisit.contains(URI.create(matcher.group()).toURL())) {
				// Match URLs and save them
				urlsToVisit.add(URI.create(matcher.group()).toURL());
				urlsFound++;
			}
		}

		stream.close();
		websiteData.close();

		return new int[] {emailsFound, urlsFound};
	}

	/**
	 * Crawl the next URL in {@code urlsToVisit}
	 * 
	 * @return 	An array of result data. Index 0 is the number of new emails found, index 1 is the number of new URLs found.
	 */
	public int[] crawlNext() {
		if (urlsToVisit.isEmpty()) {
			return new int[] {0, 0};
		}

		boolean isAccessible;
		int[] results = {};

		do {
			try {
				URL next = urlsToVisit.poll();
				urlsVisited.add(next);
				results = crawl(next);
				isAccessible = true;
			}
			catch (IOException e) {
				isAccessible = false;
			}
		} while (!isAccessible);

		return results;
	}

	/**
	 * Return the list of emails that have currently been found
	 * 
	 * @return The list of emails that have currently been found
	 */
	public ArrayList<String> getEmails() {
		return emails;
	}

	/**
	 * Return the list of URLs that have currently been found
	 * 
	 * @return The list of URLs that have currently been found
	 */
	public Queue<URL> getURLs() {
		return urlsToVisit;
	}

	/**
	 * Show the results once crawling is finished
	 */
	public void showResults() {
		System.out.println("Results");
		System.out.println("------------------------------------------------------------");

		System.out.println("\nEmails Found:");
		for (String email: emails) {
			System.out.printf("\t%s\n", email);
		}

		System.out.println("\nURLs Visited:");
		for (URL url: urlsVisited) {
			System.out.printf("\t%s\n", url);
		}

		System.out.println("\nURLs Left to Visit:");
		for (URL url: urlsToVisit) {
			System.out.printf("\t%s\n", url);
		}
	}

	/**
	 * Launch the web crawler's command-line interface
	 */
	public void runCLI() {
		Scanner scanner = new Scanner(System.in);
		URL url = getValidURL(scanner);
		urlsToVisit.add(url);

		boolean crawlNext;

		do {
			SysControl.clearConsole();
			System.out.println("\nCrawling...");

			int[] results = crawlNext();
			int urlsLeft = urlsToVisit.size();

			// Print information about the web crawling results
			System.out.printf("Visited %s\n", urlsVisited.getLast().toString());
			System.out.printf("\tFound %d new email%s\n", results[0], (results[0] == 1)? "" : "s");
			System.out.printf("\tFound %d new URL%s\n", results[1], (results[1] == 1)? "" : "s");
			System.out.printf("\nThere %s currently %d URL%s left to visit\n", (urlsLeft == 1)? "is" : "are", urlsToVisit.size(), (urlsLeft == 1)? "" : "s");
			
			// Prompt the user to continue
			System.out.print("Would you like to crawl the next URL (y/n)? ");
			crawlNext = scanner.next().toLowerCase().equals("y");
		} while (crawlNext);

		SysControl.clearConsole();
		showResults();

		scanner.close();
	}
}