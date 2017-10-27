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

public abstract class FxEventHandler implements ActionEventResponse
{

	/*
	 * List of all root menu definitions
	 */
	public ArrayList<MenuDefinition> rootMenuDefinitions;
	/*
	 * Mapping of all menu items to the corresponding definition
	 */
	public HashMap<MenuItem, MenuDefinition> menuDefinitionMap;

	/*
	 * Mapping of all menu items to the corresponding definition
	 */
	public HashMap<MenuDefinition, MenuItem> definitionMenuMap;
	/*
	 * Currently selected menu item
	 */
	public ObjectProperty<Object> menuItemSelected;
	/*
	 * Object returned from response to most recently selected menu item, null if nothing returned
	 */
	public ObjectProperty<Object> selectionActionResult;

	/*
	 * Constructor with root menu definitions specified
	 * 
	 * @param root_menus - collection of root menu definitions (if there are any)
	 */
	public FxEventHandler(MenuDefinition... root_menus)
	{
		initializeComponents();
		loadMenuDefinitions(true, root_menus);
	}

	/*
	 * Loads new menu definitions
	 * 
	 * @param clear_existing - clear existing menu items before adding new definitions
	 * 
	 * @param root_menus - collection of root menu definitions to be added
	 */
	public void loadMenuDefinitions(boolean clear_existing, MenuDefinition... root_menus)
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

	public MenuItem getMenuItemFromDefinition(MenuDefinition def)
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
		rootMenuDefinitions = new ArrayList<MenuDefinition>();
		menuDefinitionMap = new HashMap<MenuItem, MenuDefinition>();
		definitionMenuMap = new HashMap<MenuDefinition, MenuItem>();
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

	private void initializeMenus(MenuDefinition... menus)
	{
		for (MenuDefinition menuInfo : menus)
		{
			if (!menuDefinitionMap.containsValue(menuInfo))
			{
				MenuItem subMenu = new MenuItem(menuInfo.label());
				if (MenuDefinition.hasSubItems(menuInfo))
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

	private MenuItem initializeMenuElements(MenuDefinition info)
	{
		MenuItem menu = null;
		if (!menuDefinitionMap.containsValue(info))
		{
			menu = new MenuItem(info.label());

			if (MenuDefinition.hasSubItems(info))
			{
				menu = new Menu(info.label());
				for (MenuDefinition subMenuInfo : info.subMenuItems())
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

	public ArrayList<Menu> getRootMenus()
	{
		ArrayList<Menu> rootMenus = new ArrayList<Menu>();
		for (MenuItem rootItem : getRootMenuItems())
		{
			try
			{
				Menu rootMenu = (Menu) rootItem;
				rootMenus.add(rootMenu);
			} catch (Exception e)
			{

			}
		}
		return rootMenus;
	}
}
