package com.be3short.jfx.gui.dualmenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUILauncher extends Application
{

	private static GUI gui;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setScene(new Scene(gui.getWindow()));
		primaryStage.show();
	}

	static void launchWindow(GUI new_gui)
	{
		gui = new_gui;
	}
}
