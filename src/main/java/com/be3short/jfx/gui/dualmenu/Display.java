package com.be3short.jfx.gui.dualmenu;

import com.be3short.ui.menu.MenuHandler;
import javafx.scene.layout.Pane;

public interface Display extends MenuHandler
{

	public String getLabel();

	public Pane getDisplay();
}
