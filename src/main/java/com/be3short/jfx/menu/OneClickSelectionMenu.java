package com.be3short.jfx.menu;

import java.util.ArrayList;

import com.be3short.jfx.event.menu.MenuDefinition;
import com.be3short.data.cloning.ObjectCloner;
import com.be3short.jfx.event.menu.FxEventHandler;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class OneClickSelectionMenu extends Menu
{

	ArrayList<String> options;
	String selectionPrefix;
	String selectionPrompt;
	MenuDefinition id;
	FxEventHandler handler;

	public OneClickSelectionMenu(String prompt, String prefix, ArrayList<String> options, FxEventHandler handler,
	MenuDefinition id)
	{
		this.handler = handler;
		this.id = id;
		this.options = (ArrayList<String>) ObjectCloner.xmlClone(options);
		selectionPrefix = prefix;
		selectionPrompt = prompt;
		setText(prompt);
		initialize();

	}

	private void initialize()
	{
		for (String item : options)
		{
			MenuItem menu = new MenuItem(item);
			menu.setOnAction(new EventHandler<ActionEvent>()
			{

				public void handle(ActionEvent e)
				{
					handler.menuItemSelected.set(id);
					handler.respondToEvent(item);
					if (selectionPrefix != null)
					{
						setText(selectionPrefix + ": " + item);
					}

				}
			});
			getItems().add(menu);
		}
	}
}