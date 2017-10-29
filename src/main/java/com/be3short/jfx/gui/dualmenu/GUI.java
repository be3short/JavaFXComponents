package com.be3short.jfx.gui.dualmenu;

import com.be3short.ui.menu.MenuDefinition;
import com.be3short.ui.menu.MenuHandler;
import com.be3short.ui.menu.MenuInstance;
import java.util.Collection;
import java.util.HashMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI<T extends MenuHandler> extends Application
{

	private Stage stage;
	private String guiName;
	private BorderPane window;
	private BorderPane menuPane;
	private MenuBar mainMenuBar;
	private T mainMenu;
	private HashMap<Display, MenuBar> displayMenus;
	private HashMap<MenuDefinition, Display> displays;
	private Display openingDisplay;

	// Layout Configuration
	// private Pos mainMenuPosition; // position of main menu
	// private Pos displayMenuPosition; // position of display menu

	public GUI(T main_menu, Display... displays)
	{
		setup("", main_menu, displays);

	}

	public GUI()
	{

	}

	public GUI(String gui_name, T main_menu, Display... displays)
	{
		setup(gui_name, main_menu, displays);

	}

	private void prepare(T main_menu, Display... displays)
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

	public void setup(String gui_name, T main_menu, Display... displays)
	{
		guiName = gui_name;
		prepare(main_menu, displays);
		createGUI();
		setupAllMenus();
		labelStage();
	}

	private void createGUI()
	{
		window = new BorderPane();
		menuPane = new BorderPane();
		window.setCenter(openingDisplay.getDisplay());
	}

	public BorderPane getWindow()
	{
		return window;
	}

	public T getMainMenu()
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

	public void start()
	{
		GUILauncher.launchWindow(this);
	}

	public void start(Stage stage)
	{
		this.stage = stage;
		stage.setScene(new Scene(window));
		labelStage();
		stage.show();
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
			labelStage();
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

		menuPane.setCenter(mainMenuBar);
		menuPane.setRight(displayMenus.get(openingDisplay));
		window.setTop(menuPane);

	}

	private MenuBar setupMenuBar(MenuHandler menu)
	{
		MenuBar menuBar = new MenuBar();
		for (MenuDefinition menuDefinition : menu.getMenu().subMenuItems())
		{
			Menu subMenu = null;
			if (menuDefinition.subMenuItems() != null)
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
			if (menuItem.subMenuItems() != null)
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

	private void labelStage()
	{
		if (stage != null)
		{
			String stageName = "";
			if (guiName.length() > 0)
			{
				stageName = guiName + " : ";
			}
			stageName += openingDisplay.getLabel();
			stage.setTitle(stageName);
		}
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
