package com.be3short.jfx.file;

import java.io.File;
import java.util.HashMap;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MultiFileBrowser
{

	private ToolBar buttons;
	private BorderPane browseWindow;
	private BorderPane previewWindow;
	private TextArea previewText;
	private TabPane directoryTabs;
	private StringProperty selectedFile;
	private BooleanProperty browseTreeVisible;
	private HashMap<MultiFileBrowserButton, Button> buttonMap;

	public MultiFileBrowser()
	{
		initialize();
	}

	private void initialize()
	{
		initializeContainers();
		initializeButtons();
		loadContainers();
	}

	private void initializeContainers()
	{
		previewWindow = new BorderPane();
		browseWindow = new BorderPane();
		directoryTabs = new TabPane();
		browseTreeVisible = new SimpleBooleanProperty(true);
		selectedFile = new SimpleStringProperty(null);
		previewText = new TextArea();
		buttons = new ToolBar();
		directoryTabs.setSide(Side.TOP);
		buttonMap = new HashMap<MultiFileBrowserButton, Button>();
	}

	private void loadContainers()
	{
		previewWindow.setCenter(previewText);
		browseWindow.setCenter(directoryTabs);
		browseWindow.setBottom(buttons);
		Tab mainBrowser = new Tab("Files");
		mainBrowser.setContent((new FileBrowser(previewText, selectedFile, browseTreeVisible)).getWindow());
		mainBrowser.setClosable(false);
		directoryTabs.getTabs().add(mainBrowser);
	}

	private void initializeButtons()
	{
		Button browse = new Button("Browse");
		browse.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{
				try
				{
					browseTreeVisible.setValue(!browseTreeVisible.get());
				} catch (Exception e)
				{

				}
			}
		});
		buttonMap.put(MultiFileBrowserButton.BROWSE, browse);
		Button open = new Button("Open");
		open.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{
				try
				{
					File root = new DirectoryChooser().showDialog(new Stage());
					Tab mainBrowser = new Tab("#" + directoryTabs.getTabs().size());
					mainBrowser
					.setContent((new FileBrowser(previewText, selectedFile, browseTreeVisible, root.getAbsolutePath()))
					.getWindow());
					directoryTabs.getTabs().add(mainBrowser);
					directoryTabs.getSelectionModel().select(mainBrowser);
				} catch (Exception e)
				{

				}
			}
		});
		buttonMap.put(MultiFileBrowserButton.OPEN, open);
		buttonMap.put(MultiFileBrowserButton.PREVIEW, new Button("Preview"));
		buttonMap.put(MultiFileBrowserButton.ADD, new Button("Add"));
		buttonMap.put(MultiFileBrowserButton.LOAD, new Button("Load"));
		buttons.getItems().addAll(buttonMap.values());// open, browse, preview, add);
	}

	public BorderPane getBrowseWindow()
	{
		return browseWindow;
	}

	public BorderPane getPreviewWindow()
	{
		return previewWindow;
	}

	public static enum MultiFileBrowserButton
	{
		BROWSE,
		OPEN,
		PREVIEW,
		ADD,
		LOAD;
	}

	public HashMap<MultiFileBrowserButton, Button> getButtonMap()
	{
		return buttonMap;
	}
}
