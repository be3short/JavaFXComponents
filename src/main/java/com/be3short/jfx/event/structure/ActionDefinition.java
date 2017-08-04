package com.be3short.jfx.event.structure;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Generic definition of a menu to suite a variety of uses. Is used to generate
 * a Java FX menu, providing a simple way to identify and handle menu events.
 */
public interface ActionDefinition
{

	/*
	 * Label to be displayed for the menu item
	 */
	public String label();

	/*
	 * Sub menu item definitions of the current menu definition, if there are
	 * any
	 */
	public ActionDefinition[] subMenuItems();

	/*
	 * Menu item definitions that make up the first layer of the menu, ie file,
	 * edit, view, etc are the root items a common menu bar.
	 * 
	 * @return array of the root menu item definitions
	 */
	public ActionDefinition[] rootItems();

	/*
	 * Determines if the menu has sub menu items
	 * 
	 * @param info - menu definition to be evaluated
	 * 
	 * @return true if menu definition has sub menu items, false otherwise
	 */
	public static boolean hasSubItems(ActionDefinition info)
	{
		Boolean hasSubItems = false;
		if (info.subMenuItems() != null)
		{
			if (info.subMenuItems().length > 0)
			{
				hasSubItems = true;
			}
		}
		return hasSubItems;
	}

	/*
	 * Determines if the menu has the specified sub menu item
	 * 
	 * @param info - menu definition to be checked
	 * 
	 * @param item - potential sub menu definition
	 * 
	 * @return true if menu definition has sub menu item, false otherwise
	 */
	public static boolean containsSubItem(ActionDefinition root, ActionDefinition item)
	{
		Boolean hasSubItems = false;
		if (root.subMenuItems() != null)
		{
			if (root.subMenuItems().length > 0)
			{
				hasSubItems = Arrays.asList(root.subMenuItems()).contains(item);
			}
		}
		return hasSubItems;
	}
}
