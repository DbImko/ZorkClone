package org.zorkclone.core.model.core;

public interface ItemContainer extends HasStatus {

	public void addItem(String itemName);

	public void removeItem(String itemName);

	/**
	 * Returns <tt>true</tt> if container contains the element
	 * 
	 * @param itemName
	 *            Element name
	 * @return <tt>true</tt> if this container contains the specified element
	 */
	public boolean contains(String itemName);

	/**
	 * Whether contains item or not
	 * 
	 * @return true if container contains items
	 */
	public boolean hasItems();
	
	public boolean hasTriggers();

	/**
	 * Returns string with list of items for printing on screen
	 * 
	 * @return string with list of items
	 */
	public String itemsToString();

	/**
	 * Returns status of element
	 * 
	 * @return status of element
	 */
	public String getStatus();

	/**
	 * Sets new status
	 * 
	 * @param status
	 */
	public void setStatus(String status);
}
