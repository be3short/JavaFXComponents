package com.be3short.jfx.config.size;

import com.be3short.obj.access.MethodAccessor;
import com.be3short.io.xml.XMLParser;
import com.be3short.jfx.input.fieldeditors.ProtectedTextArea;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdjustSizePopup extends Application
{

	static SizeParameters params;

	public static SizeParameters getSizeParameters()
	{
		return getSizeParameters(0, 0);
	}

	public static SizeParameters getSizeParameters(Integer width, Integer height)
	{
		params = null;
		final Stage stage = new Stage();
		Integer setWidth = width;
		Integer setHeight = height;
		ProtectedTextArea widthInput = new ProtectedTextArea(null, null, setWidth, "Width");
		ProtectedTextArea heightInput = new ProtectedTextArea(null, null, setHeight, "Height");
		GridPane pane = new GridPane();
		pane.add(widthInput.mainPane, 0, 0);
		pane.add(heightInput.mainPane, 0, 1);
		Button button = new Button("Accept");
		button.setOnAction(new EventHandler<ActionEvent>()
		{

			public void handle(ActionEvent event)
			{
				params = new SizeParameters((Integer) widthInput.getInputValue(),
				(Integer) heightInput.getInputValue());
				stage.close();

			}
		});
		pane.add(button, 0, 2);
		stage.setScene(new Scene(pane, 200, 200));
		stage.showAndWait();
		return params;
	}

	public static void main(String args[])
	{
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// TODO Auto-generated method stub
		SizeParameters p = getSizeParameters();
		System.out.println(p);
	}

}
