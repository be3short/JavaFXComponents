package bs.fx.gui.inputs;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public abstract class UserInput<T>
{

	protected ObjectProperty<T> selection;
	protected Node input;
	protected Node status;

	public ObjectProperty<T> getInput()
	{
		return selection;
	}

	public Node getField()
	{
		return input;
	}

	public Node getStatus()
	{
		return status;
	}

}
