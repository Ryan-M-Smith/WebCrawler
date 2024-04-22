//
// FILENAME: ListDisplay.java | Web Crawler
// GROUP: Ryan, Simon, Suyog
// DESCRIPTION: A component that displays data in a list
// CREATED: 2024-04-20 @ 5:08 PM
//

package gui;

import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.util.Collection;
import java.util.Iterator;

/**
 * A component that visually displays some linear collection of items 
 * 
 * @param <T> The type of elements to store in the {@code ListDisplay}
 */
public class ListDisplay<T> extends JScrollPane {
	private final static int COLUMNS = 1;
	private JTable list;
	
	private DefaultTableModel model = new DefaultTableModel() {		
		@Override
		public int getColumnCount() {
			return COLUMNS;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	/**
	 * Construct a new {@code ListDisplay} object
	 * 
	 * @param 	width 		The width of the display
	 * @param 	height 		The height of the display
	 * @param 	headerText 	The display's header text
	 * 
	 * @author 				Ryan Smith
	 */
	public ListDisplay(int width, int height, String headerText) {
		list = new JTable(model);
		list.getColumnModel().getColumn(0).setHeaderValue(headerText);
		
		this.setMaximumSize(new Dimension(width, height));
		this.setViewportView(list);
	}

	/**
	 * Add a new item to the end of the {@code ListDisplay}
	 * 
	 * @param 	item 	The item to add to the list
	 * 
	 * @author 			Ryan Smith
	 */
	public void addItem(T item) {
		model.addRow(new Object[] {item});
	}

	/**
	 * Remove the row at index {@code index} and update the list accordingly
	 * 
	 * @param 	index 						The index of the row to remove
	 * 
	 * @throws 	IndexOutOfBoundsException	If {@code index} is invalid
	 * 
	 * @author 								Ryan Smith		
	 */
	public void removeItem(int index) {
		model.removeRow(index);
	}

	/**
	 * Clear the {@code ListDisplay}
	 * 
	 * @author Ryan Smith
	 */
	public void clear() {
		model.setRowCount(0);
	}

	/**
	 * Display the contents of a collection in the {@code ListDisplay}
	 * 
	 * @param 	collection 	The collection to display
	 * 
	 * @author 				Ryan Smith
	 */
	public void display(Collection<T> collection) {
		Iterator<T> iter = collection.iterator();
		while (iter.hasNext()) {
			addItem(iter.next());
		}
	}

	/**
	 * Update the contents of the {@code ListDisplay} from {@code collection}
	 * 
	 * @param 	collection 	The collection to update with
	 */
	public void update(Collection<T> collection) {
		@SuppressWarnings("unchecked")
		T[] array = (T[])collection.toArray();
		
		for (int i = model.getRowCount() + 1; i < array.length; i++) {
			addItem(array[i]);
		}
	}

	/**
	 * Get the number of rows in the list
	 * 
	 * @return The number of rows in the list
	 * 
	 * @author Ryan Smith
	 */
	public int getRowCount() {
		return model.getRowCount();
	}

	/**
	 * Get the number of columns in the list
	 * 
	 * @return The number of columns in the list
	 * 
	 * @author Ryan Smith
	 */
	public int getColumnCount() {
		return model.getColumnCount();
	}
}