package com.be3short.jfx.gui.auto;
import javafx.application.Application;
import javafx.stage.Stage;

public class AutoGUIFactory extends Application
{

	public static void newAutoGUI(Class auto_class)
	{
		AutoGUI a = new AutoGUI();
		try
		{
			a.start(new Stage());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// TODO Auto-generated method stub

	}

}
