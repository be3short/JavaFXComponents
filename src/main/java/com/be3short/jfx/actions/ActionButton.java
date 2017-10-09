package com.be3short.jfx.actions;

import bs.commons.objects.access.MethodAccessor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ActionButton // extends MethodExecutor
{

	private Object[] setParameters = null;
	private Object actionClass;
	private String actionId;
	private Button button; // gui button

	public ActionButton(String text, Object event_handler, String action_id)
	{
		actionClass = event_handler;
		actionId = action_id;
		setupButton(text);
	}

	public ActionButton(String text, String action_id, Object event_handler, String... parameter_ids)
	{
		actionClass = event_handler;
		actionId = action_id;
		setupButton(text);
	}

	public ActionButton(Object event_handler, String text, String action_id, Object... set_parameters)
	{
		actionClass = event_handler;
		actionId = action_id;
		setParameters = set_parameters;
		setupButton(text);
	}

	public ActionButton(String text, Object executor)
	{
		actionClass = executor;
		setupButton(text);
	}

	public void skinButton(String css_file)
	{
		button.getStylesheets().add(css_file);
	}

	private void setupButton(String button_text)
	{
		button = new Button(button_text);
		button.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{
				if (setParameters != null)
				{
					MethodAccessor.executeMethod(actionClass, actionId, setParameters);
				} else
				{
					MethodAccessor.executeMethod(actionClass, actionId);
				}
			}
		});
	}

	public Button getButton()
	{
		return button;
	}
}
