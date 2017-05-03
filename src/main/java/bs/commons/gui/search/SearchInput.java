package bs.commons.gui.search;

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
	private Object methods;
	private StringProperty currentSelection;

	public SearchInput(Object search_object, String key_typed_method, String text_cleared_method)
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
					//MethodAccessor.executeMethod(methods, key_typed_method, searchArea.getText());
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}