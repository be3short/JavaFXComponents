package com.be3short.jfx.event.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public abstract class ActionEventHandler implements ActionEventResponse
{

	/*
	 * List of all root menu definitions
	 */
	public ArrayList<ActionDefinition> rootMenuDefinitions;
	/*
	 * Mapping of all menu items to the corresponding definition
	 */
	public HashMap<MenuItem, ActionDefinition> menuDefinitionMap;

	/*
	 * Mapping of all menu items to the corresponding definition
	 */
	public HashMap<ActionDefinition, MenuItem> definitionMenuMap;
	/*
	 * Currently selected menu item
	 */
	public ObjectProperty<Object> menuItemSelected;
	/*
	 * Object returned from response to most recently selected menu item, null
	 * if nothing returned
	 */
	public ObjectProperty<Object> selectionActionResult;

	/*
	 * Constructor with root menu definitions specified
	 * 
	 * @param root_menus - collection of root menu definitions (if there are
	 * any)
	 */
	public ActionEventHandler(ActionDefinition... root_menus)
	{
		initializeComponents();
		loadMenuDefinitions(true, root_menus);
	}

	/*
	 * Loads new menu definitions
	 * 
	 * @param clear_existing - clear existing menu items before adding new
	 * definitions
	 * 
	 * @param root_menus - collection of root menu definitions to be added
	 */
	public void loadMenuDefinitions(boolean clear_existing, ActionDefinition... root_menus)
	{
		if (clear_existing)
		{
			clearData();
		}
		rootMenuDefinitions.addAll(Arrays.asList(root_menus));
		initializeMenus(root_menus);
	}

	public ArrayList<MenuItem> getRootMenuItems()
	{
		HashMap<String, MenuItem> itemMap = new HashMap<String, MenuItem>();
		ArrayList<MenuItem> items = new ArrayList<MenuItem>();
		for (MenuItem roots : menuDefinitionMap.keySet())
		{
			if (rootMenuDefinitions.contains(menuDefinitionMap.get(roots)))
			{
				itemMap.put(menuDefinitionMap.get(roots).label(), roots);
			}
		}
		ArrayList<String> alphabeticalNames = new ArrayList<String>(itemMap.keySet());
		Collections.sort(alphabeticalNames);
		for (String itemName : alphabeticalNames)
		{
			items.add(itemMap.get(itemName));
		}
		return items;
	}

	public MenuItem getMenuItemFromDefinition(ActionDefinition def)
	{
		MenuItem ret = null;
		if (definitionMenuMap.containsKey(def))
		{
			ret = definitionMenuMap.get(def);
		}
		return ret;
	}

	private void clearData()
	{
		rootMenuDefinitions.clear();
		menuDefinitionMap.clear();
		menuItemSelected.set(null);
		selectionActionResult.set(null);
	}

	private void initializeComponents()
	{
		rootMenuDefinitions = new ArrayList<ActionDefinition>();
		menuDefinitionMap = new HashMap<MenuItem, ActionDefinition>();
		definitionMenuMap = new HashMap<ActionDefinition, MenuItem>();
		menuItemSelected = new SimpleObjectProperty<Object>();
		selectionActionResult = new SimpleObjectProperty<Object>();
		clearData();
	}

	private void initializeMenuActions(MenuItem menu)
	{
		menu.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{
				menuItemSelected.set(menuDefinitionMap.get(menu));
				Object result = respondToEvent(menuDefinitionMap.get(menu));
				selectionActionResult.set(result);
				// menuItemSelected.set(menuDefinitionMap.get(menu));
			}
		});
	}

	private void initializeMenus(ActionDefinition... menus)
	{
		for (ActionDefinition menuInfo : menus)
		{
			if (!menuDefinitionMap.containsValue(menuInfo))
			{
				MenuItem subMenu = new MenuItem(menuInfo.label());
				if (ActionDefinition.hasSubItems(menuInfo))
				{
					subMenu = initializeMenuElements(menuInfo);
				} else
				{
					initializeMenuActions(subMenu);

				}
				menuDefinitionMap.put(subMenu, menuInfo);
				definitionMenuMap.put(menuInfo, subMenu);
			}
		}

	}

	private MenuItem initializeMenuElements(ActionDefinition info)
	{
		MenuItem menu = null;
		if (!menuDefinitionMap.containsValue(info))
		{
			menu = new MenuItem(info.label());

			if (ActionDefinition.hasSubItems(info))
			{
				menu = new Menu(info.label());
				for (ActionDefinition subMenuInfo : info.subMenuItems())
				{
					MenuItem subMenu = initializeMenuElements(subMenuInfo);
					if (subMenu != null)
					{
						((Menu) menu).getItems().add(subMenu);
						initializeMenuActions(subMenu);
						menuDefinitionMap.put(subMenu, subMenuInfo);
						definitionMenuMap.put(subMenuInfo, subMenu);
					}
				}

			} else
			{
				initializeMenuActions(menu);
			}
		}
		return menu;
	}

}