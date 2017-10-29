package com.be3short.jfx.gui.dualmenu.test;

import com.be3short.jfx.gui.dualmenu.Display;
import com.be3short.ui.menu.MenuDefinition;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GUITestDisplay implements Display
{

	private Label label;
	private Label menu;
	private BorderPane pane;
	private MenuDefinition menuDef;

	public GUITestDisplay(String label, MenuDefinition menu_def)
	{
		this.label = new Label(label);
		menu = new Label("menu");
		pane = new BorderPane();
		pane.setCenter(new VBox(this.label, menu));
		menuDef = menu_def;
	}

	@Override
	public Object[] getEventParameters(MenuDefinition arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MenuDefinition getMenu()
	{
		// TODO Auto-generated method stub
		return menuDef;
	}

	@Override
	public Object handleEvent(MenuDefinition arg0, Object... arg1)
	{
		if (MenuDefinition.containsSubItem(menuDef, arg0))
		{
			menu.setText(arg0.label());
		}
		return null;
	}

	@Override
	public String getLabel()
	{
		// TODO Auto-generated method stub
		return label.getText();
	}

	@Override
	public Pane getDisplay()
	{
		// TODO Auto-generated method stub
		return pane;
	}
}
