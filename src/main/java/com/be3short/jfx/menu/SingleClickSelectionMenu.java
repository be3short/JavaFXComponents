package com.be3short.jfx.menu;

import com.be3short.data.cloning.ObjectCloner;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class SingleClickSelectionMenu extends Menu
{

	ArrayList<String> options;
	public final StringProperty selection;
	String selectionPrefix;
	String selectionPrompt;

	public SingleClickSelectionMenu(String prompt, String prefix, ArrayList<String> options)
	{
		this.options = (ArrayList<String>) ObjectCloner.xmlClone(options);
		selection = new SimpleStringProperty(prompt);
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
					selection.set(item);
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
