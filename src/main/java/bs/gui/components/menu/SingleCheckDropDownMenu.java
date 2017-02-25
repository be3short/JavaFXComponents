package bs.gui.components.menu;

import java.util.ArrayList;
import java.util.HashMap;

import bs.commons.objects.execution.ExternalMethodExecutor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

public class SingleCheckDropDownMenu

{

	private ExternalMethodExecutor parent;
	private Object actionId;
	private Object locationId;
	private ComboBox<String> choiceBox;
	private Object selection;
	private String prompt;
	private HashMap<String, Object> selections;

	public SingleCheckDropDownMenu(ExternalMethodExecutor parent, Object action_id, String prompt,
	HashMap<String, Object> selections)
	{
		this.parent = parent;
		this.actionId = action_id;

		this.selections = selections;
		this.prompt = prompt;
		System.out.println(selections.keySet().toString());
		setupMenu();
		// choiceBox = new ChoiceBox<String>();
		// choiceBox.getItems().addAll(this.selections.keySet());
		// choiceBox.setsel

	}

	public SingleCheckDropDownMenu(ExternalMethodExecutor parent, Object action_id, String prompt,
	ArrayList<String> selections)
	{
		this.parent = parent;
		this.actionId = action_id;
		this.selections = getSelectionsMap(selections);
		this.prompt = prompt;
		System.out.println(this.selections.keySet().toString());
		setupMenu();
		// choiceBox = new ChoiceBox<String>();
		// choiceBox.getItems().addAll(this.selections.keySet());
		// choiceBox.setsel

	}

	public ComboBox getChoiceBox()
	{
		return choiceBox;
	}

	private HashMap<String, Object> getSelectionsMap(ArrayList<String> selections)
	{
		HashMap<String, Object> selectionMap = new HashMap<String, Object>();
		for (String obj : selections)
		{
			selectionMap.put(obj, obj);
		}
		return selectionMap;
	}

	private void setupMenu()
	{
		choiceBox = new ComboBox<String>(FXCollections.observableArrayList(this.selections.keySet()));
		choiceBox.setPromptText(prompt);
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
		{

			public void changed(ObservableValue ov, Number value, Number new_value)
			{
				System.out.println(selections.get(ov.getValue()));
				parent.executeMethod(prompt, choiceBox.getSelectionModel().getSelectedItem());
				// parent.HandleInputEvent(actionId,
				// selections.get(selections.keySet().toArray(new String[selections.size()])[new_value.intValue()]),
				// locationId);
			}
		});
	}
}