package ro.medapp1.dummy;


/**
 * Represents an item in a ToDo list
 */
public class ToDoItem {

	/**
	 * Item text
	 */
	@com.google.gson.annotations.SerializedName("text")
	private String Text;

	/**
	 * Item Id
	 */
	@com.google.gson.annotations.SerializedName("id")
	private String Id;

	public ToDoItem() {

	}

	@Override
	public String toString() {
		return getText();
	}

	/**
	 * Initializes a new ToDoItem
	 * 
	 * @param text
	 *            The item text
	 * @param id
	 *            The item id
	 */
	public ToDoItem(String text, String id) {
		this.setText(text);
		this.setId(id);
	}

	/**
	 * Returns the item text
	 */
	public String getText() {
		return Text;
	}

	/**
	 * Sets the item text
	 * 
	 * @param text
	 *            text to set
	 */
	public final void setText(String text) {
		this.Text = text;
	}

	/**
	 * Returns the item id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * Sets the item id
	 * 
	 * @param id
	 *            id to set
	 */
	public final void setId(String id) {
		this.Id = id;
	}


}