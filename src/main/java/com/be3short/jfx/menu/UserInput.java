package com.be3short.jfx.menu;

import com.be3short.obj.access.MethodAccessor;
import javafx.scene.layout.BorderPane;

public class UserInput
{

	public BorderPane mainPane;
	public Object updateMethod;
	public String updateMethodId;
	public String name;
	public Object defaultValue;

	public boolean checkIfValidInput()
	{
		try
		{
			Object val = MethodAccessor.executeMethod(this, Actions.getInput);
			if (val == null)
			{
				return false;
			} else
			{
				return true;
			}
		} catch (Exception e)
		{
			return false;
		}
	}

	public boolean executeUpdate()
	{
		try
		{
			Object val = MethodAccessor.executeMethod(this, Actions.getInput);
			if (val != null)
			{
				// System.out.println(updateMethodId);
				if (updateMethod != null)
				{
					MethodAccessor.executeMethod(updateMethod, updateMethodId, val);
				}
				return true;
			} else
			{
				return false;
			}
		} catch (Exception e)
		{
			if (updateMethodId == null)
			{
				return true;

			} else
			{
				return false;
			}
		}
	}

	public static class Actions
	{

		public static final String getInput = "Update";
	}

}
