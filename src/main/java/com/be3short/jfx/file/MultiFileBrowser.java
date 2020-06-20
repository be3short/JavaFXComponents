
package com.be3short.jfx.file;

import java.io.File;
import java.util.HashMap;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MultiFileBrowser {

	private ToolBar buttons;

	private ToolBar extraButtons;

	private BorderPane browseWindow;

	private BorderPane previewWindow;

	private TextArea previewText;

	private TabPane directoryTabs;

	private StringProperty selectedFile;

	public StringProperty getSelectedFile() {

		return selectedFile;
	}

	private BooleanProperty browseTreeVisible;

	private HashMap<MultiFileBrowserButton, Button> buttonMap;

	private SplitPane viewSplitter;

	public MultiFileBrowser() {

		initialize();
	}

	public void addButton(Button... buttons) {

		extraButtons.getItems().addAll(buttons);
	}

	public void addButton(ToggleButton... buttons) {

		extraButtons.getItems().addAll(buttons);
	}

	private void initialize() {

		initializeContainers();
		initializeButtons();
		loadContainers();
	}

	private void initializeContainers() {

		previewWindow = new BorderPane();
		browseWindow = new BorderPane();
		directoryTabs = new TabPane();
		browseTreeVisible = new SimpleBooleanProperty(true);
		selectedFile = new SimpleStringProperty(null);
		previewText = new TextArea();
		buttons = new ToolBar();
		extraButtons = new ToolBar();
		viewSplitter = new SplitPane();
		viewSplitter.setOrientation(Orientation.VERTICAL);
		directoryTabs.setSide(Side.TOP);
		buttonMap = new HashMap<MultiFileBrowserButton, Button>();
	}

	private void loadContainers() {

		previewWindow.setCenter(previewText);
		previewText.setMinHeight(0.0);
		viewSplitter.getItems().addAll(directoryTabs, previewWindow);// , previewWindow);
		browseWindow.setCenter(viewSplitter);
		BorderPane buttonPane = new BorderPane();
		buttonPane.setCenter(buttons);
		buttonPane.setRight(extraButtons);
		browseWindow.setBottom(buttonPane);
		Tab mainBrowser = new Tab("Files");

		mainBrowser.setContent((new FileBrowser(previewText, selectedFile, browseTreeVisible)).getWindow());
		mainBrowser.setClosable(false);
		directoryTabs.getTabs().add(mainBrowser);
	}

	private void initializeButtons() {

		ToggleButton browse = new ToggleButton("Browse");
		browse.setSelected(true);
		browse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					browseTreeVisible.setValue(browse.isSelected());
				} catch (Exception e) {

				}
			}
		});
		Button open = new Button("Open");
		open.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					File root = new DirectoryChooser().showDialog(new Stage());
					Tab mainBrowser = new Tab(root.getName());// "#" + directoryTabs.getTabs().size());
					mainBrowser.setContent(
							(new FileBrowser(previewText, selectedFile, browseTreeVisible, root.getAbsolutePath()))
									.getWindow());
					directoryTabs.getTabs().add(mainBrowser);
					directoryTabs.getSelectionModel().select(mainBrowser);
				} catch (Exception e) {

				}
			}
		});
		buttonMap.put(MultiFileBrowserButton.OPEN, open);
		ToggleButton preview = new ToggleButton("Preview");
		preview.setSelected(true);
		preview.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {

					if (!preview.isSelected()) {
						if (viewSplitter.getItems().contains(previewWindow)) {
							viewSplitter.getItems().remove(previewWindow);
						}
					} else if (!viewSplitter.getItems().contains(previewWindow)) {
						viewSplitter.getItems().add(previewWindow);
					}
				} catch (Exception e) {

				}
			}
		});
		buttonMap.put(MultiFileBrowserButton.ADD, new Button("Add"));
		buttonMap.put(MultiFileBrowserButton.LOAD, new Button("Load"));
		buttons.getItems().addAll(browse, preview);
		buttons.getItems().addAll(buttonMap.values());// open, browse, preview, add);
	}

	public BorderPane getBrowseWindow() {

		return browseWindow;
	}

	public BorderPane getPreviewWindow() {

		return previewWindow;
	}

	public static enum MultiFileBrowserButton {
		BROWSE,
		OPEN,
		PREVIEW,
		ADD,
		LOAD; }

	public HashMap<MultiFileBrowserButton, Button> getButtonMap() {

		return buttonMap;
	}
}
