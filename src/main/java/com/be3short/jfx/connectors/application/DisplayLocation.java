package com.be3short.jfx.connectors.application;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public enum DisplayLocation
{
	TOP,
	BOTTOM,
	LEFT,
	RIGHT,
	CENTER;

	public void loadContent(BorderPane pane, Node content)
	{
		switch (this)
		{
		case TOP:
		{
			pane.setTop(content);
			break;
		}
		case BOTTOM:
		{
			pane.setBottom(content);
			break;
		}
		case LEFT:
		{
			pane.setLeft(content);
			break;
		}
		case RIGHT:
		{
			pane.setRight(content);
			break;
		}
		case CENTER:
		{
			pane.setCenter(content);
			break;
		}
		default:
		{
			break;
		}
		}
	}
}
