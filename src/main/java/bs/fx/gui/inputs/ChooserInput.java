package bs.fx.gui.inputs;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;

public class ChooserInput<T> extends UserInput<T>
{

	private Class<T> choiceClass;
	private T defaultChoice;
	private ChoiceBox<String> choices;
	private HashMap<String, T> choiceMap;

	public ChooserInput(Class<T> choice_class, T default_choice)
	{
		selection = new SimpleObjectProperty<ArrayList<T>>();
		ArrayList<T> choices = new ArrayList<T>();
		choices.add(default_choice);/// defaultChoice);
		selection.set(choices);
		status = null;
		choiceMap = new HashMap<String, T>();
		defaultChoice = default_choice;
		choiceClass = choice_class;
		String defaultChoiceName = null;
		if (choice_class.equals(Boolean.class))
		{
			defaultChoiceName = initializeBoolean();
		} else if (choice_class.isEnum())
		{
			defaultChoiceName = initializeEnum();
		}
		initializeChoiceBox(defaultChoiceName);
		// selection.set(choiceMap.get(defaultChoiceName));
	}

	public ChooserInput(Class<T> choice_class, HashMap<String, T> mapping, String default_choice)
	{
		selection = new SimpleObjectProperty<ArrayList<T>>();
		ArrayList<T> choices = new ArrayList<T>();
		choices.add(mapping.get(default_choice));/// defaultChoice);
		selection.set(choices);
		defaultChoice = mapping.get(default_choice);
		choiceClass = choice_class;
		choiceMap = mapping;
		initializeChoiceBox(default_choice);
		// selection.get().(choiceMap.get(default_choice));
	}

	private String initializeBoolean()
	{
		choiceMap.put("True", choiceClass.cast(Boolean.TRUE));
		choiceMap.put("False", choiceClass.cast(Boolean.FALSE));
		if ((Boolean) defaultChoice)
		{
			return "True";
		} else
		{
			return "False";
		}

	}

	private String initializeEnum()
	{
		String defaultName = getChoiceName(defaultChoice);
		for (T aChoice : choiceClass.getEnumConstants())
		{
			String getEnumName = getChoiceName(aChoice);
			choiceMap.put(getEnumName, aChoice);
		}
		return defaultName;
	}

	private String initializeMap()
	{
		String defaultName = getChoiceName(defaultChoice);
		for (T aChoice : choiceClass.getEnumConstants())
		{
			String getEnumName = getChoiceName(aChoice);
			choiceMap.put(getEnumName, aChoice);
		}
		return defaultName;
	}

	public String getChoiceName(T cenum)
	{

		String enumName = ((Enum) cenum).name();
		String[] splitByUnderscore = enumName.split("_");
		enumName = "";
		for (int i = 0; i < splitByUnderscore.length; i++)
		{
			if (i > 0)
			{
				enumName += " ";
			}
			enumName += splitByUnderscore[i].substring(0, 1) + splitByUnderscore[i].substring(1).toLowerCase();
		}

		return enumName;
	}

	public void initializeChoiceBox(String default_name)
	{
		choices = new ChoiceBox<String>();
		choices.getItems().addAll(choiceMap.keySet());
		choices.getSelectionModel().select(default_name);
		choices.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{
				selection.get().clear();
				ArrayList<T> choicez = new ArrayList<T>();
				choicez.add(choiceMap.get(choices.getSelectionModel().getSelectedItem()));
				selection.set(choicez);
			}
		});
		input = choices;
	}

}
