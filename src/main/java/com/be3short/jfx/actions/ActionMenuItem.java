package com.be3short.jfx.actions;

import bs.commons.objects.access.MethodAccessor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class ActionMenuItem extends MenuItem
{

	private String methodId;
	private Object methodObject;

	public ActionMenuItem(String text, Object method_object, String method_id)
	{
		super(text);
		methodObject = method_object;
		methodId = method_id;
		setupAction();
	}

	private void setupAction()
	{
		super.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{

				MethodAccessor.executeMethod(methodObject, methodId);

			}
		});
	}
}
