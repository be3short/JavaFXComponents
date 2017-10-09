package com.be3short.jfx.button;

import com.be3short.jfx.actions.ToggleAction;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

public class ToggleMenuButton extends SplitMenuButton
{

	private ToggleAction toggleResponse;
	private BooleanProperty status;

	public BooleanProperty getStatus()
	{
		return status;
	}

	private Label enabledLabel;
	private Label disabledLabel;

	public ToggleMenuButton(ToggleAction toggle_response, Label enabled, Label disabled)
	{
		this.toggleResponse = toggle_response;
		this.enabledLabel = enabled;
		this.disabledLabel = disabled;
		initializeButton(new ArrayList<MenuItem>());
	}

	public ToggleMenuButton(ToggleAction toggle_response, Label enabled, Label disabled, ArrayList<MenuItem> menu)
	{
		this.toggleResponse = toggle_response;
		this.enabledLabel = enabled;
		this.disabledLabel = disabled;
		initializeButton(menu);

	}

	private void initializeButton(ArrayList<MenuItem> menu)
	{
		status = new SimpleBooleanProperty(true);// .set(false);
		super.setGraphic(disabledLabel);
		super.getItems().addAll(menu);
		super.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{
				if (status.getValue())
				{

					toggleResponse.disabledReaction();
					setGraphic(disabledLabel);
				} else
				{

					toggleResponse.enabledReaction();
					setGraphic(enabledLabel);
				}
				status.setValue(!status.getValue());
			}
		});
	}
}
