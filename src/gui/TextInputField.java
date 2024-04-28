//
// FILENAME: TextInputField.java | Web Crawler
// GROUP: Ryan, Simon, Suyog
// DESCRIPTION: A text input component that allows the user to "submit" their input
// CREATED: 2024-04-18 @ 11:43 AM
//

package gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

/**
 * A text input component with a "submit" button.
 * 
 * {@code TextInputField} is a special {@code JPanel} that wraps a {@code JTextField} and {@code JButton}.
 * It allows the user to "submit" the text entered in the text field when the button is clicked. The components
 * are displayed side-by-side in a horizontal {@code BoxLayout}.
 * 
 * @see JPanel
 * @see JTextField
 * @see JButton
 */
public class TextInputField extends JPanel {
	private JTextField 	textField;
	private JButton 	button;
	private String 		tooltip;

	/**
	 * Construct a new {@code TextInputField} object
	 * 
	 * @param 	width 			The width of the text field
	 * @param 	height 			The height of the text field
	 * @param 	defaultText 	The input component's default text 
	 * @param 	buttonText 		The button's default text
	 */
	public TextInputField(int width, int height, String tooltip, String buttonText) {
		this.tooltip = tooltip;

		textField = new JTextField(tooltip);
		button = new JButton(buttonText);

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		textField.setMaximumSize(new Dimension(width, height));
		
		this.add(textField);
		this.add(button);
	}

	/**
	 * Define the "submit" button's functionality
	 * 
	 * @param 	action 	A function to call when the button is clicked
	 */
	public void onClick(ActionListener action) {
		button.addActionListener(action);
	}

	/**
	 * Get the input text
	 * 
	 * @return The input text
	 */
	public String getText() {
		return textField.getText();
	}

	/**
	 * Get the text field
	 * 
	 * @return The text field
	 */
	public JTextField getTextField() {
		return textField;
	}

	/**
	 * Get the input field's "submit" button
	 * 
	 * @return The button
	 */
	public JButton getButton() {
		return button;
	}

	/**
	 * Enable or disable the "submit" button
	 * 
	 * @param 	enabled 	The button's new state
	 */
	public void setButtonEnabled(boolean enabled) {
		button.setEnabled(enabled);
	}

	/**
	 * Reset the text field
	 */
	public void reset() {
		textField.setText(tooltip);
	}

} 