package com.be3short.jfx.connectors;

import java.util.HashMap;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ApplicationPropertiesInterface

{

	public ObjectProperty<Object> getNewDataLoaded()
	{
		return newDataLoaded;
	}

	public void newDataLoaded(Object data)
	{
		newDataLoaded.set(null);
		newDataLoaded.set(data);
	}

	private ObjectProperty<Object> newDataLoaded;

	public ApplicationPropertiesInterface(Stage main_stage, BorderPane main_display, String title_prefix)
	{
		mainStage = main_stage;
		mainDisplay = main_display;
		title = new HashMap<Node, String>();
		titlePrefix = title_prefix;
		initialize();
		newDataLoaded = new SimpleObjectProperty<Object>(null);

		// TODO Auto-generated constructor stub
	}

	private ObjectProperty<Node> displayElement;
	private HashMap<Node, String> title;
	private BorderPane mainDisplay;
	private Stage mainStage;
	private ObjectProperty<String> stageTitle;
	private String titlePrefix;

	private void initialize()
	{
		displayElement = new SimpleObjectProperty<Node>(null);
		stageTitle = new SimpleObjectProperty<String>(null);
		initializeDisplayListener();
		initializeStageTitleListener();
	}

	private void initializeDisplayListener()
	{

		displayElement.addListener(new ChangeListener<Node>()
		{

			@Override
			public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue)
			{
				displayElement.set(null);
				mainDisplay.setCenter(newValue);

			}
		});

	}

	private void initializeStageTitleListener()
	{

		stageTitle.addListener(new ChangeListener<String>()
		{

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				stageTitle.set(null);
				mainStage.setTitle(newValue);

			}
		});

	}

	public void updateMainDisplay(Node display)
	{
		displayElement.set(display);
		if (title.containsKey(display))
		{
			updateStageTitle(titlePrefix + " : " + title.get(display));
		} else
		{
			updateStageTitle(titlePrefix);
		}
	}

	public void updateStageTitle(String title)
	{
		stageTitle.set(title);
	}

	public void addDisplayTitle(Node display, String title)
	{
		this.title.put(display, title);
	}
}
