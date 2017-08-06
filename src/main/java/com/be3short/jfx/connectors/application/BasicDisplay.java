package com.be3short.jfx.connectors.application;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BasicDisplay implements DisplayActions
{

	protected BorderPane pane;
	private BasicDisplaySettings settings;

	public BasicDisplay(DisplayLocation menu_location, DisplayLocation content_location)
	{

		settings = new BasicDisplaySettings(menu_location, content_location);
		initialize();
	}

	public BasicDisplay(BasicDisplaySettings settings)
	{

		this.settings = settings;
		initialize();
	}

	private void initialize()
	{
		pane = new BorderPane();

	}

	@Override
	public void updateMenuDisplay(Node menu)
	{
		settings.menuPosition.loadContent(pane, menu);

	}

	@Override
	public void updateContentDisplay(Node display)
	{
		settings.contentPosition.loadContent(pane, display);

	}

}
