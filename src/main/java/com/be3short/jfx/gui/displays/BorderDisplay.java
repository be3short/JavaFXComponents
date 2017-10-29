package com.be3short.jfx.gui.displays;

import com.be3short.jfx.gui.dualmenu.Display;
import com.be3short.ui.menu.MenuDefinition;
import com.be3short.ui.menu.MenuHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public abstract class BorderDisplay<T extends MenuHandler> implements Display
{

	private T menu;
	private BorderPane display;
	private String name;

	public BorderDisplay(T menu, String name)
	{
		prepare(menu, name);
	}

	@Override
	public Object[] getEventParameters(MenuDefinition arg0)
	{
		// TODO Auto-generated method stub
		return menu.getEventParameters(arg0);
	}

	@Override
	public MenuDefinition getMenu()
	{
		// TODO Auto-generated method stub
		return menu.getMenu();
	}

	@Override
	public Object handleEvent(MenuDefinition arg0, Object... arg1)
	{
		return menu.handleEvent(arg0, menu.getEventParameters(arg0));
	}

	private void prepare(T menu, String name)
	{
		this.menu = menu;
		this.name = name;
		display = new BorderPane();
	}

	protected void initialize()
	{
		initializeComponents();
		createContainers();
		loadContainers();
	}

	protected void initializeComponents()
	{

	}

	protected void createContainers()
	{

	}

	protected void loadContainers()
	{

	}

	@Override
	public String getLabel()
	{
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public Pane getDisplay()
	{
		// TODO Auto-generated method stub
		return display;
	}

	public BorderPane getPane()
	{
		// TODO Auto-generated method stub
		return display;
	}

}
