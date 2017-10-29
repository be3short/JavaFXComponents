package com.be3short.jfx.gui.dualmenu.test;

import com.be3short.jfx.gui.dualmenu.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUITest extends Application
{

	public static void main(String args[])
	{
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		GUITestDisplay display1 = new GUITestDisplay("Display 1", GUITestMenuA.MENU);
		GUITestDisplay display2 = new GUITestDisplay("Display 2", GUITestMenuA.MENU);
		GUI gui = new GUI(new GUITestMenuHandler(), display1, display2);
		gui.launch(primaryStage);
	}
}
