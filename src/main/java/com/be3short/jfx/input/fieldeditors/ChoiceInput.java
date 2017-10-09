package com.be3short.jfx.input.fieldeditors;

import java.util.ArrayList;
import java.util.HashMap;

import bs.commons.objects.access.ChoiceAccessor;
import bs.commons.objects.execution.ExternalMethodExecutor;
import bs.commons.objects.execution.MethodId;
import bs.commons.objects.manipulation.XMLParser;
import com.be3short.jfx.menu.UserInput;
import com.be3short.jfx.menu.UserInput.Actions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ChoiceInput extends UserInput
{

	private ComboBox<String> choiceBox;
	ArrayList<String> inputOptions;
	HashMap<String, Object> choices;

	public ChoiceInput(Object update, String update_id, Object default_value, String name, Object... choice_enum)
	{
		mainPane = new BorderPane();
		updateMethod = update;
		updateMethodId = update_id;
		defaultValue = default_value;
		this.name = name;
		choices = ChoiceAccessor.getChoices(choice_enum);
		initialize();
	}

	public void updateExternalMethod(Object update, String update_id)
	{
		updateMethod = update;
		updateMethodId = update_id;
	}

	private void initialize()
	{
		initializeGUI();
	}

	private void initializeGUI()
	{
		Text title = new Text(name);
		setupMenu();

		mainPane.setLeft(title);
		HBox choices = new HBox(choiceBox);
		choices.setAlignment(Pos.CENTER);
		mainPane.setRight(choices);
		//choiceBox.getSelectionModel()
		//	.select(ChoiceAccessor.getChoices(defaultValue).keySet().toArray(new String[1])[0]);
	}

	@MethodId(id = Actions.getInput)
	public Object getInputValue()
	{
		Object val = choices.get(choiceBox.getSelectionModel().getSelectedItem());
		return val;
	}

	private void setupMenu()
	{
		choiceBox = new ComboBox<String>(FXCollections.observableArrayList(choices.keySet()));
		choiceBox.getSelectionModel().select(0);
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
		{

			public void changed(ObservableValue ov, Number value, Number new_value)
			{
				executeUpdate();

				// parent.HandleInputEvent(actionId,
				// selections.get(selections.keySet().toArray(new String[selections.size()])[new_value.intValue()]),
				// locationId);
			}
		});
	}
}
