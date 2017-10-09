package com.be3short.jfx.gui.auto;

import bs.commons.objects.access.MethodAccessor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AutoGUI extends Application
{

	BorderPane mainPane;
	Class selectedClass;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// TODO Auto-generated method stub
		createContainers();
		primaryStage.setScene(new Scene(mainPane, 400, 400));
		primaryStage.show();
	}

	private void createContainers()
	{
		mainPane = new BorderPane();
		createClassFinder();

	}

	private void createClassFinder()
	{
		TextField classField = new TextField();
		Button button = new Button("Find");
		Label classLabel = new Label();
		button.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{
				try
				{
					selectedClass = ClassLoader.getSystemClassLoader().loadClass(classField.getText());
					classLabel.setText(selectedClass.getName());
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		BorderPane finderPane = new BorderPane();
		finderPane.setLeft(button);
		finderPane.setRight(classField);
		finderPane.setBottom(classLabel);
		mainPane.setCenter(finderPane);
	}

	public static void main(String args[])
	{
		launch(args);
	}

}