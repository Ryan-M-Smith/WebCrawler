//
// FILENAME: Separator.java | Web Crawler
// GROUP: Ryan, Simon, Suyog
// DESCRIPTION: A JSeparator of arbitary size and orientation
// CREATED: 2024-04-21 @ 2:12 AM
//

package gui;

import java.awt.Dimension;
import javax.swing.JSeparator;

/**
 * A {@code JSeparator} of arbitary size and orientation
 * 
 * @author Ryan Smith
 */
public class Separator extends JSeparator {
	public static enum Orientation {HORIZONTAL, VERTICAL};
	private Orientation orientation;

	public Separator(Orientation orientation, int length) {
		super(orientation.ordinal());
		this.orientation = orientation;

		// Set the length of the separator
		switch (orientation) {
			case HORIZONTAL:
				this.setMaximumSize(new Dimension(length, 0));
				break;
			case VERTICAL:
				this.setMaximumSize(new Dimension(0, length));
				break;
		}
	}

	@Override
	public int getOrientation() {
		return orientation.ordinal();
	}
} 