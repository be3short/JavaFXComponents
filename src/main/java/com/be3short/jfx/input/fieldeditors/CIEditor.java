
package com.be3short.jfx.input.fieldeditors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CIEditor extends Application {

	public static class RandomData {

		public String str = " bla bla ";

		// public float ff = (float) 0.0;

		// public double dd = 25.0;
		public Boolean bool = true;
		// public Integer ii = 100;

		public RandomData() {

		}
	}

	public static void main(String args[]) {

		Application.launch();
	}

	@Override
	public void start(Stage arg0) throws Exception {

		RandomData rd = new RandomData();
		ClassInstanceEditor ci = new ClassInstanceEditor(rd);
		BorderPane pane = new BorderPane();
		pane.setCenter(ci.mainPane);
		Scene scene = new Scene(pane, 1200, 800);

		arg0.setScene(scene);

		// Label label = new Label("Hello");
		// pane.setCenter(label);
		arg0.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {

				try {
					stop();
					Platform.exit();
					System.exit(0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		arg0.show();
	}

}
