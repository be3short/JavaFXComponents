package bs.gui.components.input.fieldeditors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import bs.commons.objects.execution.ExternalFieldUpdate;
import bs.gui.components.menu.UserInput;

public class InputSorter
{

	public static final ArrayList<Class> textClasses = new ArrayList<Class>(Arrays.asList(new Class[]
	{ Double.class, Integer.class, String.class, Number.class }));

	public static UserInput getAppropriateInput(ExternalFieldUpdate field_updater)
	{

		UserInput input = null;
		if (field_updater.field != null)
		{
			Class fieldValClass = field_updater.value.getClass();
			if (textClasses.contains(fieldValClass))
			{
				input = new ProtectedTextArea(field_updater, "Update", field_updater.value, field_updater.name);
			} else if (fieldValClass.equals(Boolean.class))
			{
				input = new BooleanInput(field_updater, "Update", field_updater.value, field_updater.name);
			} else if (fieldValClass.isEnum())
			{

				input = new ChoiceInput(field_updater, "Update", field_updater.value, field_updater.name,
				fieldValClass.getEnumConstants());
			}
		}
		return input;
	}
}
