package com.be3short.jfx.inputs;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public abstract class UserInput<T>
{

	protected ObjectProperty<ArrayList<T>> selection;
	protected Node input;
	protected Node status;

	public ObjectProperty<ArrayList<T>> getInput()
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
