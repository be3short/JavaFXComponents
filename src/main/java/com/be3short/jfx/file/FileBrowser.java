package com.be3short.jfx.file;

import com.be3short.io.general.FileSystemInteractor;
import com.be3short.jfx.event.menu.FxEventHandler;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.eclipse.fx.ui.controls.filesystem.DirectoryTreeView;
import org.eclipse.fx.ui.controls.filesystem.DirectoryView;
import org.eclipse.fx.ui.controls.filesystem.IconSize;
import org.eclipse.fx.ui.controls.filesystem.ResourceItem;
import org.eclipse.fx.ui.controls.filesystem.RootDirItem;

public class FileBrowser extends FxEventHandler
{

	private static ArrayList<String> allowedExtensions = new ArrayList<String>();
	private BorderPane window;

	public BorderPane getWindow()
	{
		return window;
	}

	private TextArea previewText;
	private SplitPane adjustmentDisplay;
	private DirectoryTreeView directoryTree;
	private DirectoryView directoryView;
	private StringProperty selectedFile;
	private static RootDirItem rootDirItem;

	public FileBrowser(TextArea preview_text, StringProperty selected_file, BooleanProperty browse, String root_path)
	{
		previewText = preview_text;
		selectedFile = selected_file;
		rootDirItem = ResourceItem.createObservedPath(Paths.get(root_path));
		initialize(browse);
		directoryView.setDir(directoryTree.getRootDirectories().get(0));
	}

	public FileBrowser(TextArea preview_text, StringProperty selected_file, BooleanProperty browse)
	{
		previewText = preview_text;
		selectedFile = selected_file;
		rootDirItem = ResourceItem.createObservedPath(Paths.get(System.getProperty("user.home")));
		initialize(browse);
	}

	public static void addAllowedExtension(String... extensions)
	{
		for (String extension : extensions)
		{
			if (!allowedExtensions.contains(extension))
			{
				allowedExtensions.add(extension);
			}
		}
	}

	private void initialize(BooleanProperty browse)
	{
		initializeContainers();
		initializeDirectoryViews();
		initializeButtons(browse);
	}

	private void initializeContainers()
	{
		window = new BorderPane();
		adjustmentDisplay = new SplitPane();
		window.setCenter(adjustmentDisplay);
	}

	private void initializeDirectoryViews()
	{

		directoryTree = new DirectoryTreeView();
		directoryTree.setIconSize(IconSize.SMALL);
		directoryTree.setRootDirectories(FXCollections.observableArrayList(rootDirItem));

		directoryView = new DirectoryView();
		directoryView.setIconSize(IconSize.SMALL);

		directoryTree.getSelectedItems().addListener((Observable o) ->
		{
			if (!directoryTree.getSelectedItems().isEmpty())
			{
				directoryView.setDir(directoryTree.getSelectedItems().get(0));
			} else
			{
				directoryView.setDir(null);
			}
		});
		directoryView.getSelectedItems().addListener((Observable o) ->
		{
			if (!directoryView.getSelectedItems().isEmpty())
			{
				// System.out.println(directoryView.getSelectedItems().get(0));
				selectedFile.setValue(directoryView.getSelectedItems().get(0).getUri().split("file:")[1]);
				// System.out.println(selectedFile.getValue());
				if (hasExtension(selectedFile.getValue()))
				{
					Thread debugStatusThread = new Thread(new Runnable()
					{

						public void run()
						{
							previewText
							.setText(FileSystemInteractor.getFileContentsAsString(new File(selectedFile.getValue())));
						}

					});
					debugStatusThread.start();

				}
			}
		});
		adjustmentDisplay.getItems().addAll(directoryTree, directoryView);
	}

	public static boolean hasExtension(String file_name)
	{
		for (String extension : allowedExtensions)
		{
			if (file_name.contains(extension))
			{
				return true;
			}
		}
		return false;
	}

	private void initializeButtons(BooleanProperty browse)
	{

		browse.addListener(new ChangeListener<Boolean>()
		{

			//

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
			{

				if (adjustmentDisplay.getItems().contains(directoryTree))
				{
					adjustmentDisplay.getItems().remove(directoryTree);
				} else
				{
					adjustmentDisplay.getItems().add(0, directoryTree);
				}
			}

			//
		});
	}

	public static void main(String[] args)
	{
		Application.launch(args);
		rootDirItem.dispose();
	}

	@Override
	public Object respondToEvent(Object event_flag)
	{
		// TODO Auto-generated method stub
		return null;
	}
}