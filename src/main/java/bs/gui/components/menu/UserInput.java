package bs.gui.components.menu;

import bs.commons.objects.access.MethodAccessor;
import bs.commons.objects.execution.ExternalFieldUpdate;
import bs.commons.objects.execution.ExternalMethodExecutor;
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

		Object val = MethodAccessor.executeMethod(this, Actions.getInput);
		if (val == null)
		{
			return false;
		} else
		{
			return true;
		}
	}

	public boolean executeUpdate()
	{
		try
		{
			Object val = MethodAccessor.executeMethod(this, Actions.getInput);
			if (val != null)
			{
				System.out.println(updateMethodId);
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

		public static final String getInput = "Get Input";
	}

}
