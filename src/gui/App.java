//
// FILENAME: App.java | Web Crawler
// GROUP: Ryan, Simon, Suyog
// DESCRIPTION: Java Swing desktop app for the web crawler
// CREATED: 2024-04-17 @ 3:32 PM
//

package gui;

import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.net.*;
import javax.swing.*;

import gui.Separator.Orientation;
import webcrawler.WebCrawler;

public final class App {
	private WebCrawler webCrawler;

	private final Dimension SCREEN_SIZE 	= Toolkit.getDefaultToolkit().getScreenSize();
	private final int SCREEN_WIDTH  		= (int)SCREEN_SIZE.getWidth();
	private final int SCREEN_HEIGHT 		= (int)SCREEN_SIZE.getHeight();
	private final int DEFAULT_WINDOW_WIDTH 	= 1000;
	private final int DEFAULT_WINDOW_HEIGHT = 750;

	private int windowWidth;
	private int windowHeight;

	public App() {
		windowWidth = DEFAULT_WINDOW_WIDTH;
		windowHeight = DEFAULT_WINDOW_HEIGHT;
		webCrawler = new WebCrawler();
	}

	public App(int width, int height) {
		windowWidth = width;
		windowHeight = height;
		webCrawler = new WebCrawler();
	}

	private void loadGUI() throws FontFormatException, IOException {
		JFrame frame = new JFrame("Web Crawler");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final Font ARCHIVO_REGULAR = Font.createFont(Font.TRUETYPE_FONT, new File("static/Archivo-Regular.ttf"));

		// try {
		// 	// Set System L&F
		// 	System.out.println(Arrays.toString(UIManager.getInstalledLookAndFeels()));
		// 	UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		// } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		// 	// handle exception
		// 	System.out.println("Here");
		// }

		// Create a vertical layout within the window
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel title = new JLabel("Web Crawler");
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		//title.setMaximumSize(new Dimension(500, 48));
		title.setFont(ARCHIVO_REGULAR.deriveFont(48.0f));
		panel.add(title);

		// Add spacing between the title and the text field
		Separator separator1 = new Separator(Orientation.VERTICAL, 30);
		panel.add(separator1);

		//
		// Build a panel for the URL entry and the start button
		//
		TextInputField inputField = new TextInputField(400, 30, "Enter a URL to start crawling", "Start");
		panel.add(inputField);

		// Add spacing between the text field and the output
		Separator separator2 = new Separator(Orientation.VERTICAL, 100);
		panel.add(separator2);

		//
		// Build a panel to display the web crawler's output
		//

		JPanel outputField = new JPanel();
		outputField.setLayout(new BoxLayout(outputField, BoxLayout.X_AXIS));

		// The emails found
		ListDisplay<String> emailsFound = new ListDisplay<>(300, 375, "Emails Found");
		outputField.add(emailsFound);

		// Add spacing between the tables
		outputField.add(Box.createRigidArea(new Dimension(30, 375)));

		// The URLs found
		ListDisplay<URL> urlsLeft = new ListDisplay<>(300, 375, "URLs Left to Crawl");
		outputField.add(urlsLeft);

		panel.add(outputField);

		// Add a button to advance the web crawler to the next available URL
		JButton nextURLButton = new JButton("Crawl Next URL");
		nextURLButton.addActionListener(event -> {
			urlsLeft.removeItem(0);
			webCrawler.crawlNext();

			emailsFound.update(webCrawler.getEmails());
			urlsLeft.update(webCrawler.getURLs());
		});
		inputField.add(nextURLButton);
		nextURLButton.setEnabled(false);

		inputField.onClick(event -> {
			try {
				URL url = readURL(inputField);
				webCrawler.crawl(url);
			}
			catch (URISyntaxException | IllegalArgumentException | IOException e) {
				// Show an error message to the user
				JOptionPane.showMessageDialog(
					frame,
					"The URL entered is invalid. Please try again with a different URL.",
					"URL Error",
					JOptionPane.ERROR_MESSAGE
				);
				return;
			}

			emailsFound.display(webCrawler.getEmails());
			urlsLeft.display(webCrawler.getURLs());

			nextURLButton.setEnabled(true);
			inputField.setButtonEnabled(false);
		});

		frame.pack();
		frame.add(panel);

		// Center the window on the screen
		frame.setBounds(
			(SCREEN_WIDTH - windowWidth) / 2,
			(SCREEN_HEIGHT - windowHeight) / 2,
			SCREEN_HEIGHT,
			windowHeight
		);

		frame.setVisible(true);
	}

	private static URL readURL(TextInputField inputField) throws URISyntaxException, MalformedURLException, IllegalArgumentException {
		String urlString = inputField.getText();
		URL url = new URI(urlString).toURL();
		return url;
	}

	/**
	 * Launch the application.
	 * 
	 * @author Ryan Smith
	 */
	public void launch() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					loadGUI();
				}
				catch (FontFormatException | IOException e) {
					System.err.println(e.getMessage());
				}
			}
		});
	}
}