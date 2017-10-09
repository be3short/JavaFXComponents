package com.be3short.jfx.button;

import com.be3short.jfx.event.menu.MenuIdentifier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class MenuBarButton extends BorderPane
{

	private String themeFile; // css file containing the theme for this button
	private String selectedStyle; // name of style to apply when button is the
									// selection
	private String unselectedStyle; // name of style to apply when button is not
									// the selection
	private ObjectProperty<MenuIdentifier> updater; // object to store the
													// method identifier when a
													// click is detected
	private Pos orientation; // orientation of the button
	private MenuIdentifier details; // text that is displayed
	private MenuBar bar = new MenuBar(); // the menu bar that is the container
	private Menu menu = new Menu(); // the menu item that is the button
	private Label label = new Label(); // the label which detects the click
										// actions

	public MenuBarButton(MenuIdentifier details, ObjectProperty<MenuIdentifier> update_object)
	{
		updater = update_object;
		this.details = details;
		initializeComponents();
		setupActions();
		setThemes();
	}

	public void setThemes() // apply defaults
	{
		setThemes("themes/default/buttons/MenuBarButton.css", ".selected", ".not-selected");
	}

	public void setThemes(String css_file_path, String selected_style, String not_selected_style)
	{
		themeFile = css_file_path;
		bar.getStylesheets().add(themeFile);
		selectedStyle = selected_style;
		unselectedStyle = not_selected_style;
	}

	private void initializeComponents()
	{
		bar = new MenuBar();
		label = new Label(details.getLabel());

		menu = new Menu();
		menu.setGraphic(label);
		bar.getMenus().add(menu);
		setCenter(bar);
	}

	private void setupActions()
	{

		label.setOnMousePressed(new EventHandler<MouseEvent>()

		{

			@Override
			public void handle(MouseEvent event)
			{
				updater.set(details);
				// IO.debug("menu bar button (" + details.getLabel() + ")
				// clicked");
				// event.consume();
			}
		});
		updater.addListener(new ChangeListener<MenuIdentifier>()
		{

			@Override
			public void changed(ObservableValue<? extends MenuIdentifier> observable, MenuIdentifier oldValue,
			MenuIdentifier newValue)
			{
				applyTheme();
			}
		});
	}

	private void applyTheme()
	{

		if (updater.get().equals(details))
		{
			menu.getStyleClass().add(selectedStyle);
			label.getStyleClass().add(selectedStyle);
		} else
		{
			menu.setStyle(unselectedStyle);
		}
	}

}

/*
 * MenuBar simMenu = new MenuBar();
 * 
 * 
 * 
 * String disabledBackground =
 * "-fx-background-fill: rgb(50,50, 50); -fx-padding: 0px;"; String disabledText
 * = "-fx-text-fill: rgb(44,44,44); -fx-padding: 0px"; String enabledBackground
 * =
 * ("-fx-background-color: rgb(30,30,30);");//menu2Label.setTextFill(Color.AQUA)
 * ; //menu2Label.setTextFill(Color.AQUA); String enabledLabel =
 * "-fx-text-fill: rgb(0,214,43); -fx-padding: 0px;"; // // String
 * disabledBackground = "-fx-text-fill: rgb(50,50, 50); -fx-padding: 0px;"; //
 * String disabledText = "-fx-text-fill: rgb(0,0,0); -fx-padding: 0px"; //
 * String enabledBackground =
 * ("-fx-background-color: rgb(0, 227,19);");//menu2Label.setTextFill(Color.AQUA
 * ); // //menu2Label.setTextFill(Color.AQUA); // String enabledLabel =
 * "-fx-background-color: rgb(50,50, 50);";
 * 
 * // menuLabel.setStyle("-fx-background-color: yellow; -fx-padding: 0px;");
 * menuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
 * 
 * @Override public void handle(MouseEvent event) { System.out.println("ge");
 * showAgentConfig(); menuLabel.setStyle(enabledLabel);
 * menu2Label.setStyle(disabledText); simMenu.setStyle(enabledBackground);
 * simMenu2.setStyle(disabledBackground); menu3Label.setStyle(disabledText);
 * simMenu3.setStyle(disabledBackground);
 * //menuLabel.setTextFill(Color.CHARTREUSE);
 * //menu2Label.setTextFill(Color.ANTIQUEWHITE); } }); /* selectedMenu = new
 * SimpleObjectProperty<Menu>(null); //
 * menu2Label.setStyle("-fx-background-color: yellow; -fx-padding: 0px;");
 * menu2Label.setOnMouseClicked(new EventHandler<MouseEvent>() {
 * 
 * @Override public void handle(MouseEvent event) { System.out.println("ge");
 * showSettingsConfig(); menu3Label.setStyle(disabledText);
 * simMenu3.setStyle(disabledBackground); menu2Label.setStyle(enabledLabel);
 * menuLabel.setStyle(disabledText);
 * simMenu2.setStyle(enabledBackground);//menu2Label.setTextFill(Color.AQUA);
 * simMenu.setStyle(disabledBackground);//menu2Label.setTextFill(Color.AQUA); }
 * }); fileMenuButton.setGraphic(menuLabel);
 * filemenu2Button.setGraphic(menu2Label);
 * filemenu3Button.setGraphic(menu3Label); menu3Label.setOnMouseClicked(new
 * EventHandler<MouseEvent>() {
 * 
 * @Override public void handle(MouseEvent event) { System.out.println("ge");
 * showSettingsConfig(); menu2Label.setStyle(disabledText);
 * simMenu2.setStyle(disabledBackground);
 * simMenu3.setStyle(enabledBackground);//menu2Label.setTextFill(Color.AQUA);
 * //menu2Label.setTextFill(Color.AQUA); menu3Label.setStyle(enabledLabel);
 * simMenu.setStyle(disabledBackground);
 * 
 * menuLabel.setStyle(disabledText);
 * 
 * }
 */