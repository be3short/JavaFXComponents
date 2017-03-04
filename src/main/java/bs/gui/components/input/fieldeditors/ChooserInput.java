package bs.gui.components.input.fieldeditors;

import java.util.HashMap;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;

public class ChooserInput<T>
{

	private ObjectProperty<T> choice;
	private Class<T> choiceClass;
	private T defaultChoice;
	private ChoiceBox<String> choices;
	private HashMap<String, T> choiceMap;

	public ChooserInput(Class<T> choice_class, T default_choice)
	{
		choice = new SimpleObjectProperty<T>(defaultChoice);
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
		String defaultName = "";
		for (T aChoice : choiceClass.getEnumConstants())
		{
			String getEnumName = getChoiceName(aChoice);
			if (aChoice.equals(defaultName))
			{
				defaultName = getEnumName;
			}
			choiceMap.put(getEnumName, aChoice);
		}
		return defaultName;
	}

	public String getChoiceName(Object cenum)
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
			enumName = splitByUnderscore[i].substring(0, 1) + splitByUnderscore[i].substring(1).toLowerCase();
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
				choice.set(choiceMap.get(choices.getSelectionModel().getSelectedItem()));
			}
		});
	}

	public ObjectProperty<T> getChoice()
	{
		return choice;
	}

	public ChoiceBox<String> getChoiceBox()
	{
		return choices;
	}
}
