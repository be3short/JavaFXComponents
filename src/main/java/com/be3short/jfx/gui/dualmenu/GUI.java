package com.be3short.jfx.gui.dualmenu;

import com.be3short.ui.menu.MenuDefinition;
import com.be3short.ui.menu.MenuHandler;
import com.be3short.ui.menu.MenuInstance;
import java.util.Collection;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI
{

	private BorderPane window;
	private BorderPane menuPane;
	private MenuBar mainMenuBar;
	private MenuHandler mainMenu;
	private HashMap<Display, MenuBar> displayMenus;
	private HashMap<MenuDefinition, Display> displays;
	private Display openingDisplay;

	// Layout Configuration
	// private Pos mainMenuPosition; // position of main menu
	// private Pos displayMenuPosition; // position of display menu

	public GUI(MenuHandler main_menu, Display... displays)
	{
		setup(main_menu, displays);

	}

	private void prepare(MenuHandler main_menu, Display... displays)
	{
		this.displayMenus = new HashMap<Display, MenuBar>();
		this.displays = new HashMap<MenuDefinition, Display>();
		mainMenu = main_menu;
		if (displays.length > 0)
		{
			openingDisplay = displays[0];
		}
		for (Display display : displays)
		{
			this.displayMenus.put(display, null);
		}
	}

	private void setup(MenuHandler main_menu, Display... displays)
	{
		prepare(main_menu, displays);
		createGUI();
		setupAllMenus();
	}

	private void createGUI()
	{
		menuPane = new BorderPane();
		menuPane.setCenter(openingDisplay.getDisplay());
	}

	public BorderPane getWindow()
	{
		return window;
	}

	public MenuHandler getMainMenu()
	{
		return mainMenu;
	}

	public Collection<Display> getDisplays()
	{
		return displays.values();
	}

	public Display getOpeningDisplay()
	{
		return openingDisplay;
	}

	public void launch(Stage stage)
	{
		stage.setScene(new Scene(window));

	}

	private void handleEvent(MenuDefinition event_id)
	{
		if (!handleToggleDisplay(event_id))
		{
			mainMenu.handleEvent(event_id, mainMenu.getEventParameters(event_id));
			for (Display display : displayMenus.keySet())
			{
				display.handleEvent(event_id, display.getEventParameters(event_id));
			}
		}
	}

	private boolean handleToggleDisplay(MenuDefinition event_id)
	{
		if (displays.containsKey(event_id))
		{
			openingDisplay = displays.get(event_id);
			window.setCenter(openingDisplay.getDisplay());
			return true;
		}
		return false;
	}

	private void setupAllMenus()
	{
		menuPane = new BorderPane();
		mainMenuBar = setupMenuBar(mainMenu);
		for (Display display : displayMenus.keySet())
		{
			MenuBar displayMenu = setupMenuBar(display);
			displayMenus.put(display, displayMenu);
			MenuInstance displayMenuItem = new MenuInstance(display.getLabel());
			displays.put(displayMenuItem, display);
		}
		MenuInstance displaySelector = new MenuInstance("Display",
		displays.keySet().toArray(new MenuDefinition[displays.size()]));
		Menu displaySelectMenu = setupMenu(displaySelector);
		mainMenuBar.getMenus().add(displaySelectMenu);
		menuPane.setCenter(mainMenuBar);
		placeMenus();
	}

	private void placeMenus()
	{
		window.setTop(mainMenuBar);
	}

	private MenuBar setupMenuBar(MenuHandler menu)
	{
		MenuBar menuBar = new MenuBar();
		for (MenuDefinition menuDefinition : menu.getMenu().subMenuItems())
		{
			Menu subMenu = null;
			if (MenuDefinition.hasSubItems(menuDefinition))
			{
				subMenu = setupMenu(menuDefinition);

			} else
			{
				subMenu = new Menu(menuDefinition.label());

				subMenu.setOnAction(new EventHandler<ActionEvent>()
				{

					public void handle(ActionEvent event)
					{
						handleEvent(menuDefinition);
					}
				});

			}
			menuBar.getMenus().add(subMenu);
		}
		return menuBar;
	}

	private Menu setupMenu(MenuDefinition menu)
	{
		Menu m = new Menu(menu.label());

		for (MenuDefinition menuItem : menu.subMenuItems())
		{
			MenuItem newMenu;
			if (menuItem.subMenuItems().length > 0)
			{
				newMenu = setupMenu(menuItem);
			} else
			{
				newMenu = setupMenuItem(menuItem);
			}
			m.getItems().add(newMenu);
		}

		return m;
	}

	private MenuItem setupMenuItem(MenuDefinition menu)
	{
		MenuItem newMenuItem = new MenuItem(menu.label());
		newMenuItem.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{
				handleEvent(menu);
			}
		});
		return newMenuItem;
	}

	// public Pos getMainMenuPosition()
	// {
	// return mainMenuPosition;
	// }
	//
	//
	// public void setMainMenuPosition(Pos mainMenuPosition)
	// {
	// this.mainMenuPosition = mainMenuPosition;
	// }
	//
	//
	// public Pos getDisplayMenuPosition()
	// {
	// return displayMenuPosition;
	// }
	//
	//
	// public void setDisplayMenuPosition(Pos displayMenuPosition)
	// {
	// this.displayMenuPosition = displayMenuPosition;
	// }
}
