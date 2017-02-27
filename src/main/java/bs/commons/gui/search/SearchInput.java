package bs.commons.gui.search;

import bs.commons.objects.access.MethodAccessor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class SearchInput extends BorderPane
{

	private Button clearSearch;
	private TextField searchArea;

	private Object methods;

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
					MethodAccessor.executeMethod(methods, key_typed_method, searchArea.getText());
					searchArea.clear();
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
					MethodAccessor.executeMethod(methods, key_typed_method, searchArea.getText());
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}