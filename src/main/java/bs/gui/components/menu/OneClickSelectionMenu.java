package bs.gui.components.menu;

import java.util.ArrayList;

import com.be3short.jfx.event.menu.ActionDefinition;
import com.be3short.jfx.event.menu.ActionEventHandler;

import bs.commons.objects.manipulation.ObjectCloner;
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
	ActionDefinition id;
	ActionEventHandler handler;

	public OneClickSelectionMenu(String prompt, String prefix, ArrayList<String> options, ActionEventHandler handler,
	ActionDefinition id)
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