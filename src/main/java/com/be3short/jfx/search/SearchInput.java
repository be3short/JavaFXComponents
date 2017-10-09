package com.be3short.jfx.search;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class SearchInput extends BorderPane
{

	private Button clearSearch;
	private TextField searchArea;
	private EventResponder methods;
	private StringProperty currentSelection;

	public SearchInput(EventResponder search_object, String key_typed_method, String text_cleared_method)
	{
		methods = search_object;
		initialize(key_typed_method, text_cleared_method);
	}

	private void initialize(String key_typed_method, String text_cleared_method)
	{
		initializeComponents();
		initializeActions(key_typed_method, text_cleared_method);
	}

	private void initializeComponents()
	{
		searchArea = new TextField();
		clearSearch = new Button("X");
		searchArea.setPromptText("Search Library");
		setCenter(searchArea);
		setRight(clearSearch);
		currentSelection = new SimpleStringProperty();
		// mainPane.setRight(clearSearch);
	}

	private void initializeActions(String key_typed_method, String text_cleared_method)
	{
		clearSearch.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{
				try
				{
					searchArea.clear();
					currentSelection.setValue("");
					methods.newEvent(SearchEvent.SEARCH_CLEARED);

				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		searchArea.setOnKeyReleased(new EventHandler<KeyEvent>()
		{

			public void handle(KeyEvent ke)
			{
				try
				{
					System.out.println(searchArea.textProperty().get());
					currentSelection.setValue(searchArea.textProperty().get());
					methods.newEvent(SearchEvent.NEW_CHARACTERS, searchArea.getText());
					// MethodAccessor.executeMethod(methods, key_typed_method,
					// searchArea.getText());
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public static enum SearchEvent
	{
		NEW_CHARACTERS,
		SEARCH_CLEARED;
	}
}