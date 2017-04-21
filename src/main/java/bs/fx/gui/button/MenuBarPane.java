package bs.fx.gui.button;

import java.util.Collections;
import java.util.HashMap;

import bs.fx.organization.identifier.MenuIdentifier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MenuBarPane extends BorderPane
{

	// Display
	private String themeFile; // css file containing the theme for this button
	private String selectedStyle; // name of style to apply when button is the selection
	private String unselectedStyle; // name of style to apply when button is not the selection
	private boolean selectorsVisible; // flag to display selectors or not
	private ObjectProperty<MenuIdentifier> updater; // object to store the method identifier when a click is detected
	private Side side; // orientation of the buttons
	private VBox selectorBox; // box that contains the selection buttons
	private HashMap<MenuIdentifier, Pane> panes; // text that is displayed 
	private HashMap<MenuIdentifier, MenuBarButton> selectionButtons; // buttons that select the panes

	public MenuBarPane(HashMap<MenuIdentifier, Pane> panes)
	{
		this.panes = panes;
		side = Side.TOP;
		initialize();
	}

	public MenuBarPane(HashMap<MenuIdentifier, Pane> panes, Side side)
	{
		this.panes = panes;
		this.side = side;
		initialize();
	}

	private void initialize()
	{
		selectorsVisible = true;
		initializeFields();
		setupActions();
		initializeComponents();
		setThemes();
	}

	private void initializeFields()
	{
		updater = new SimpleObjectProperty<MenuIdentifier>(null);
		selectionButtons = new HashMap<MenuIdentifier, MenuBarButton>();
		selectorBox = new VBox();
	}

	private void initializeComponents()
	{
		HashMap<Integer, MenuIdentifier> panesByIndex = new HashMap<Integer, MenuIdentifier>();
		for (MenuIdentifier id : panes.keySet())
		{
			//MenuBarButton button = new MenuBarButton(id, updater);
			MenuBarButton button = new MenuBarButton(id, updater);

			selectionButtons.put(id, button);
			setCenter(panes.get(id));
			panesByIndex.put(id.index(), id);
		}
		placePanes(panesByIndex);

	}

	private void placePanes(HashMap<Integer, MenuIdentifier> panes_by_index)
	{
		for (Integer index : panes_by_index.keySet())
		{
			if (updater.get() == null)
			{
				selectorBox.getChildren().add(selectionButtons.get(panes_by_index.get(index)));
			}
		}
		updater.set(panes_by_index.get(Collections.min(panes_by_index.keySet())));
		setLeft(selectorBox);
	}

	public void setThemes() // apply defaults
	{
		setThemes("themes/default/buttons/MenuBarButton.css", ".selected", ".not-selected");
	}

	public void setThemes(String css_file_path, String selected_style, String not_selected_style)
	{
		themeFile = css_file_path;
		getStylesheets().add(themeFile);
		selectedStyle = selected_style;
		unselectedStyle = not_selected_style;
	}

	private void setupActions()
	{
		updater.addListener(new ChangeListener<MenuIdentifier>()
		{

			@Override
			public void changed(ObservableValue<? extends MenuIdentifier> observable, MenuIdentifier oldValue,
			MenuIdentifier newValue)
			{
				System.out.println("SHowingPane");
				setCenter(panes.get(newValue));
			}
		});
	}

}
