package com.be3short.jfx.connectors.application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BasicDisplayWindow extends BasicDisplay implements DisplayWindowActions
{

	private BasicDisplayWindowSettings windowSettings;

	public BasicDisplayWindow(Stage stage, DisplayLocation menu_location, DisplayLocation content_location)
	{
		super(menu_location, content_location);
		this.stage = stage;
		windowSettings = new BasicDisplayWindowSettings();
		initialize();
	}

	public BasicDisplayWindow(String window_prefix, Stage stage, DisplayLocation menu_location,
	DisplayLocation content_location)
	{
		super(menu_location, content_location);
		this.stage = stage;
		windowSettings = new BasicDisplayWindowSettings(window_prefix);
		initialize();
	}

	public BasicDisplayWindow(String window_prefix, Stage stage, DisplayLocation menu_location,
	DisplayLocation content_location, Double width, Double height)
	{
		super(menu_location, content_location);
		this.stage = stage;
		windowSettings = new BasicDisplayWindowSettings(window_prefix, width, height);
		initialize();
	}

	private void initialize()
	{

		Scene scene = new Scene(pane, windowSettings.width, windowSettings.height);
		stage.setScene(scene);
		updateStageTitle(null);
	}

	private Stage stage;

	@Override
	public void updateStageTitle(String title)
	{
		String newTitle = title;
		if (windowSettings.windowLabelPrefix != null)
		{
			newTitle = windowSettings.windowLabelPrefix + " : " + newTitle;
		}
		stage.setTitle(newTitle);

	}

	@Override
	public void setVisibility(boolean visible)
	{
		try
		{
			if (visible)
			{
				stage.show();
			} else
			{
				stage.hide();
			}
		} catch (Exception visibilityOverlap)
		{

		}

	}
}
