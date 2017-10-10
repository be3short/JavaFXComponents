package com.be3short.jfx.input.fieldeditors;

import java.util.ArrayList;
import java.util.HashMap;

import com.be3short.obj.access.ChoiceAccessor;
import com.be3short.obj.execution.ExternalMethodExecutor;
import com.be3short.obj.execution.MethodId;
import com.be3short.jfx.menu.UserInput;
import com.be3short.jfx.menu.UserInput.Actions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class BooleanInput extends UserInput
{

	private ComboBox<String> choiceBox;
	HashMap<String, Boolean> choices;

	public BooleanInput(Object update, String update_id, Object default_value, String name)
	{
		mainPane = new BorderPane();
		updateMethod = update;
		updateMethodId = update_id;
		defaultValue = default_value;
		this.name = name;
		choices = new HashMap<String, Boolean>();
		choices.put("true", true);
		choices.put("false", false);
		initialize();
	}

	public void updateExternalMethod(ExternalMethodExecutor update, String update_id)
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

		HBox box = new HBox(choiceBox);
		mainPane.setLeft(title);
		mainPane.setRight(box);
	}

	@MethodId(id = Actions.getInput)
	public Object getInputValue()
	{
		Boolean val = choices.get(choiceBox.getSelectionModel().getSelectedItem());
		return val;
	}

	private void setupMenu()
	{
		choiceBox = new ComboBox<String>(FXCollections.observableArrayList(choices.keySet()));
		choiceBox.getSelectionModel().select(defaultValue.toString());
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
		{

			public void changed(ObservableValue ov, Number value, Number new_value)
			{
				executeUpdate();

				// parent.HandleInputEvent(actionId,
				// selections.get(selections.keySet().toArray(new
				// String[selections.size()])[new_value.intValue()]),
				// locationId);
			}
		});
	}
}
