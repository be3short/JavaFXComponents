package com.be3short.jfx.input.fieldeditors;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import bs.commons.objects.execution.ExternalFieldUpdate;
import com.be3short.jfx.menu.UserInput;

public class InputSorter
{

	public static final ArrayList<Class> skipClasses = new ArrayList<Class>(Arrays.asList(new Class[]
	{ HashMap.class }));
	public static final ArrayList<Class> textClasses = new ArrayList<Class>(Arrays.asList(new Class[]
	{ Double.class, Integer.class, String.class, Number.class }));

	public static UserInput getAppropriateInput(ExternalFieldUpdate field_updater)
	{
		return getAppropriateInput(field_updater, false);
	}

	public static UserInput getAppropriateInput(ExternalFieldUpdate field_updater, boolean recursive)
	{

		UserInput input = null;
		if (field_updater.field != null)
		{
			try
			{
				Class fieldValClass = field_updater.value.getClass();
				System.out.println(field_updater.value.getClass().getSuperclass());
				if (!skipClasses.contains(fieldValClass) && field_updater.field.getModifiers() != Modifier.FINAL)
				{
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
					} else
					{
						input = new ClassInstanceEditor(field_updater.value);

					}
				}

			} catch (Exception nullField)
			{

			}
		}
		return input;
	}
}